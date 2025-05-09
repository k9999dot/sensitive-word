package com.github.houbb.sensitive.word.core;

import java.util.List;
import java.util.Map;

import com.github.houbb.heaven.util.guava.Guavas;
import com.github.houbb.heaven.util.util.CollectionUtil;
import com.github.houbb.sensitive.word.api.ISensitiveWord;
import com.github.houbb.sensitive.word.api.IWordCheck;
import com.github.houbb.sensitive.word.api.IWordContext;
import com.github.houbb.sensitive.word.api.IWordResult;
import com.github.houbb.sensitive.word.api.IWordResultCondition;
import com.github.houbb.sensitive.word.api.context.InnerSensitiveWordContext;
import com.github.houbb.sensitive.word.constant.enums.WordValidModeEnum;
import com.github.houbb.sensitive.word.support.check.WordCheckResult;
import com.github.houbb.sensitive.word.support.result.WordResult;
import com.github.houbb.sensitive.word.utils.InnerWordFormatUtils;

/**
 * 默认实现
 *
 * @since 0.3.2
 */
public class SensitiveWord extends AbstractSensitiveWord {

    /**
     * 0.3.2
     */
    private static final ISensitiveWord INSTANCE = new SensitiveWord();

    public static ISensitiveWord getInstance() {
        return INSTANCE;
    }

    @Override
    protected List<IWordResult> doFindAll(String string, IWordContext context) {
        return innerSensitiveWords(string, WordValidModeEnum.FAIL_OVER, context);
    }

    @Override
    protected IWordResult doFindFirst(String string, IWordContext context) {
        List<IWordResult> wordResults = innerSensitiveWords(string, WordValidModeEnum.FAIL_FAST, context);
        if (!CollectionUtil.isEmpty(wordResults)) {
            return wordResults.get(0);
        }
        return null;
    }


    /**
     * 获取敏感词列表
     *
     * @param text 文本
     * @param modeEnum 模式
     * @return 结果列表
     * @since 0.0.1
     */
    private List<IWordResult> innerSensitiveWords(final String text,
            final WordValidModeEnum modeEnum,
            final IWordContext context) {
        final IWordCheck sensitiveCheck = context.sensitiveCheck();
        List<IWordResult> resultList = Guavas.newArrayList();

        final Map<Character, Character> characterCharacterMap = InnerWordFormatUtils.formatCharsMapping(text, context);
        final InnerSensitiveWordContext checkContext = InnerSensitiveWordContext.newInstance()
                .originalText(text)
                .wordContext(context)
                .modeEnum(WordValidModeEnum.FAIL_OVER)
                .formatCharMapping(characterCharacterMap);
        final IWordResultCondition wordResultCondition = context.wordResultCondition();

        for (int i = 0; i < text.length(); i++) {
            WordCheckResult checkResult = sensitiveCheck.sensitiveCheck(i, checkContext);
            int wordLengthAllow = checkResult.wordLengthResult().wordAllowLen();
            int wordLengthDeny = checkResult.wordLengthResult().wordDenyLen();
            //如果命中的白名单长度小于黑名单，则直接对黑名单的敏感词进行保存
            if (wordLengthAllow < wordLengthDeny) {
                // 保存敏感词
                WordResult wordResult = WordResult.newInstance()
                        .startIndex(i)
                        .endIndex(i + wordLengthDeny)
                        .type(checkResult.type())
                        .word(checkResult.wordLengthResult().wordDeny());
                if (wordResultCondition.match(wordResult, text, modeEnum, context)) {
                    resultList.add(wordResult);
                    // 快速返回
                    if (WordValidModeEnum.FAIL_FAST.equals(modeEnum)) {
                        break;
                    }
                }
                // 增加 i 的步长为什么要-1，因为默认就会自增1
                i += wordLengthDeny - 1;
            } else {
                //如果命中的白名单长度大于黑名单长度，则跳过白名单个字符
                i += Math.max(0, wordLengthAllow - 1);
            }
        }

        return resultList;
    }

}
