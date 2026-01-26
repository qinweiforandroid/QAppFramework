package com.qw.framework.app.compose.list

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.qw.framework.ui.BaseActivity

/**
 * author : qinwei@agora.io
 * date : 2025/11/3 17:50
 * description :
 */
class ArticleListActivity : BaseActivity() {
    override fun setContentView() {
        setContent {

        }
    }

    @Composable
    fun ArticleList(articles: ArrayList<ArticleState>) {
        Column(modifier = Modifier.fillMaxWidth()) {
            articles.forEach {
                Card {
                    ArticleCardItem(it, Modifier.fillMaxWidth())
                }
                Spacer(modifier = Modifier.height(4.dp))
            }
        }
    }

    @Preview
    @Composable
    fun ArticleListPreview() {
        val state = ArticleState("添加图片元素", "Android")
        val articles = arrayListOf<ArticleState>()
        for (i in 0 until 10) {
            articles.add(state)
        }
        Surface(modifier = Modifier.fillMaxWidth()) {
            ArticleList(articles)
        }
    }

    @Preview(name = "light")
    @Preview(
        name = "dark",
        showBackground = true,
        uiMode = Configuration.UI_MODE_NIGHT_YES
    )
    @Composable
    fun ArticleCardItemPreview() {
        val state = ArticleState("添加图片元素", "Android")
        MaterialTheme {
            Surface {
                Card {
                    ArticleCardItem(state, Modifier.fillMaxWidth())
                }
            }
        }
    }

    @Composable
    fun ArticleCardItem(state: ArticleState, modifier: Modifier = Modifier) {
        Column(modifier = modifier.padding(8.dp)) {
            Text(
                text = state.title,
                fontSize = 16.sp,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = modifier.height(8.dp))
            Text(
                text = "作者:${state.author}",
                fontSize = 14.sp,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }

    data class ArticleState(val title: String, val author: String)

    override fun initView() {

    }

    override fun initData(savedInstanceState: Bundle?) {
    }
}