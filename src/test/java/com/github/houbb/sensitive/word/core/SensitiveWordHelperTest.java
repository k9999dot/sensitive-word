package com.github.houbb.sensitive.word.core;

import com.github.houbb.sensitive.word.api.IWordReplace;
import com.github.houbb.sensitive.word.api.IWordResult;
import com.github.houbb.sensitive.word.replace.MyWordReplace;
import com.github.houbb.sensitive.word.support.result.WordResultHandlers;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;


public class SensitiveWordHelperTest {

    /**
     * 是否包含敏感词
     */
    @Test
    public void containsTest() {
        final String text = "你这个大二货,我真想干死你这个大二货。";

        Assert.assertTrue(SensitiveWordHelper.contains(text));
    }

    /**
     * 返回所有敏感词
     */
    @Test
    public void findAllTest(){
        final String text = "你这个大二货,我真想干死你这个大二货。";

        List<String> wordList = SensitiveWordHelper.findAll(text);
        Assert.assertEquals("[二货, 干死, 二货]", wordList.toString());
    }


    /**
     * 返回所有第一个匹配的敏感词
     */
    @Test
    public void findFirstTest() {
        final String text = "你这个大二货,我真想干死你这个大二货。";

        String word = SensitiveWordHelper.findFirst(text);
        Assert.assertEquals("二货", word);
    }


//
//    /**
//     * 默认的替换策略
//     */
//    @Test
//    public void replaceTest() {
//        final String text = "五星红旗迎风飘扬，毛主席的画像屹立在天安门前。";
//
//        String result = SensitiveWordHelper.replace(text);
//        Assert.assertEquals("****迎风飘扬，***的画像屹立在***前。", result);
//    }
//
//    /**
//     * 自定义字符的替换策略
//     * @since 0.0.2
//     */
//    @Test
//    public void replaceCharTest() {
//        final String text = "五星红旗迎风飘扬，毛主席的画像屹立在天安门前。";
//
//        String result = SensitiveWordHelper.replace(text, '0');
//        Assert.assertEquals("0000迎风飘扬，000的画像屹立在000前。", result);
//    }
//
//    /**
//     * 忽略大小写
//     * @since 0.0.4
//     */
//    @Test
//    public void ignoreCaseTest() {
//        final String text = "fuCK the bad words.";
//
//        String word = SensitiveWordHelper.findFirst(text);
//        Assert.assertEquals("fuCK", word);
//    }
//
//    /**
//     * 忽略半角圆角
//     * @since 0.0.4
//     */
//    @Test
//    public void ignoreWidthTest() {
//        final String text = "ｆｕｃｋ the bad words.";
//
//        String word = SensitiveWordHelper.findFirst(text);
//        Assert.assertEquals("ｆｕｃｋ", word);
//    }
//
//    /**
//     * 自定替换策略
//     * @since 0.2.0
//     */
//    @Test
//    public void defineReplaceTest() {
//        final String text = "五星红旗迎风飘扬，毛主席的画像屹立在天安门前。";
//
//        IWordReplace replace = new MyWordReplace();
//        String result = SensitiveWordHelper.replace(text, replace);
//
//        Assert.assertEquals("国家旗帜迎风飘扬，教员的画像屹立在***前。", result);
//    }
//
//    /**
//     * 返回所有敏感词
//     * @since 0.1.0
//     */
//    @Test
//    public void findAllWordTest() {
//        final String text = "五星红旗迎风飘扬，毛主席的画像屹立在天安门前。";
//
//        List<String> wordList = SensitiveWordHelper.findAll(text, WordResultHandlers.word());
//        Assert.assertEquals("[五星红旗, 毛主席, 天安门]", wordList.toString());
//    }
//
//    /**
//     * 返回所有敏感词-包含下标志
//     * @since 0.1.0
//     */
//    @Test
//    public void findAllRawTest() {
//        final String text = "五星红旗迎风飘扬，毛主席的画像屹立在天安门前。";
//
//        List<IWordResult> wordList = SensitiveWordHelper.findAll(text, WordResultHandlers.raw());
//        Assert.assertEquals("[WordResult{startIndex=0, endIndex=4, type='WORD', word='5星红旗'}, WordResult{startIndex=9, endIndex=12, type='WORD', word='毛主席'}, WordResult{startIndex=18, endIndex=21, type='WORD', word='天安门'}]", wordList.toString());
//    }
//
//    /**
//     * 返回所有第一个匹配的敏感词
//     * @since 0.0.1
//     */
//    @Test
//    public void findFirstWordTest() {
//        final String text = "五星红旗迎风飘扬，毛主席的画像屹立在天安门前。";
//
//        String word = SensitiveWordHelper.findFirst(text, WordResultHandlers.word());
//        Assert.assertEquals("五星红旗", word);
//    }
//
//    /**
//     * 返回所有第一个匹配的敏感词
//     * @since 0.0.1
//     */
//    @Test
//    public void findFirstRawTest() {
//        final String text = "五星红旗迎风飘扬，毛主席的画像屹立在天安门前。";
//
//        IWordResult word = SensitiveWordHelper.findFirst(text, WordResultHandlers.raw());
//        Assert.assertEquals("WordResult{startIndex=0, endIndex=4, type='WORD', word='5星红旗'}", word.toString());
//    }


}
