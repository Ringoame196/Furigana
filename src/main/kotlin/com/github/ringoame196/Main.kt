package com.github.ringoame196

import com.github.ringoame196.managers.FuriganaManager
import java.util.Scanner
import kotlin.collections.component1
import kotlin.collections.component2
import kotlin.collections.iterator

val scanner = Scanner(System.`in`) // 入力を受け付ける

fun main() {
	val console = System.console()

	if (console == null) {
		println("コンソールが使用できません")
		return
	}

	val clientID = console.readPassword("Please enter Client ID:") // 入力した文字を 見えないように
	println()

	val gradeParameter = acquisitionGradeParameter()
	println()

	val huriganaManager = FuriganaManager(String(clientID), gradeParameter)

	println("ふりがなをつけたい 文章を入力してください\n")

	while(true) {
		print("[入力]:")
		val inputValue = scanner.next()
		val response = huriganaManager.post(inputValue)
		val formattedResponse = huriganaManager.formatFuriganaResponse(response ?: return)
		println("[ふりがな] $formattedResponse\n")
	}
}

private fun acquisitionGradeParameter(): Int {
	val gradeParameterMap = mapOf(
		"小1年向け" to 1,
		"小2年向け" to 2,
		"小3年向け" to 3,
		"小4年向け" to 4,
		"小5年向け" to 5,
		"小6年向け" to 6,
		"中学向け" to 7,
		"一般向け" to 8
	)

	println("[gradeパラメータ表]")
	for ((k,v) in gradeParameterMap) {
		println("$k -> $v")
	}
	println()
	print("gradeパラメータを設定してください:")
	return scanner.nextInt()
}