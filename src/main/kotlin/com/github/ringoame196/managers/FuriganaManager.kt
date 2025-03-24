package com.github.ringoame196.managers

import okhttp3.Headers
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

class FuriganaManager(private val appId: String,private val grade: Int) {
	private val url = "https://jlp.yahooapis.jp/FuriganaService/V2/furigana"

	fun post(query: String): String? {
		val client = OkHttpClient()

		// リクエストヘッダー
		val headers = Headers.Builder()
			.add("Content-Type", "application/json")
			.add("User-Agent", "Yahoo AppID: $appId")
			.build()

		// リクエストボディの設定
		val jsonParams = JSONObject().apply {
			put("id", "1234-1")
			put("jsonrpc", "2.0")
			put("method", "jlp.furiganaservice.furigana")
			put("params", JSONObject().apply {
				put("q", query)
				put("grade", grade)
			})
		}

		val body = jsonParams.toString().toRequestBody("application/json".toMediaType())

		// リクエスト作成
		val request = Request.Builder()
			.url(url)
			.post(body)
			.headers(headers)
			.build()

		client.newCall(request).execute().use { response ->
			if (!response.isSuccessful) {
				println("Error: ${response.code}")
				return null
			}
			return response.body?.string()
		}
	}

	fun formatFuriganaResponse(jsonResponse: String): String {
		val response = JSONObject(jsonResponse)
		val result = response.getJSONObject("result")
		val words = result.getJSONArray("word")

		val formattedText = StringBuilder()

		for (i in 0 until words.length()) {
			val wordObj = words.getJSONObject(i)

			// 表面の文字（surface）
			val surface = wordObj.getString("surface")
			var furiganaText = ""

			// ふりがな（furigana）がある場合
			if (wordObj.has("furigana")) {
				val furigana = wordObj.getString("furigana")
				furiganaText = "($furigana)"
			}

			// 表面の文字とふりがなを組み合わせて表示
			formattedText.append("$surface$furiganaText")
		}

		return formattedText.toString()
	}
}