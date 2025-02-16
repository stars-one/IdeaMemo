package com.ldlywt.note.utils

import com.ldlywt.note.bean.Tag
import java.util.regex.Matcher
import java.util.regex.Pattern

object TopicUtils {

    private val inputReg = "(\\#[\u4e00-\u9fa5a-zA-Z]+\\d{0,100})[\\w\\s]"
    val pattern: Pattern = Pattern.compile(inputReg)
    fun getTopicListByString(text: String): List<Tag> {
        val tagList: MutableList<Tag> = mutableListOf()
        val matcher: Matcher = pattern.matcher(text)
        while (matcher.find()) {
            val tag = text.substring(matcher.start(), matcher.end()).trim { it <= ' ' }
            tagList.add(Tag(tag = tag))

        }
        return tagList
    }
}

object CityRegexUtils {
    fun getCityByString(input: String): Pair<String, String>? {
        val lastIndex = input.lastIndexOf('@')

        if (lastIndex != -1 && lastIndex < input.length - 1) {
            val beforeLastAt = input.substring(0, lastIndex)
            val afterLastAt = input.substring(lastIndex + 1)
            return Pair(beforeLastAt, afterLastAt)
        }

        return Pair(input, "")
    }
}