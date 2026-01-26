package com.qw.framework.app.compose

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.qw.framework.app.R
import com.qw.framework.app.compose.theme.ComposeTheme

/**
 * author : qinwei@agora.io
 * date : 2026/1/26 17:13
 * description :
 */

@Composable
fun BottomBar(selectedIndex: Int = 0, onTabSelected: (Int) -> Unit = {}) {
    Row {
        TabItem(
            icon = if (selectedIndex == 0) R.drawable.btn_tab_inquiry_pressed else R.drawable.btn_tab_inquiry_normal,
            label = R.string.tab_home,
            modifier = Modifier
                .weight(1f)
                .clickable {
                    onTabSelected.invoke(0)
                },
            tint = if (selectedIndex == 0) Color.Blue else Color.Black
        )
        TabItem(
            icon = if (selectedIndex == 1) R.drawable.btn_tab_casehistory_pressed else R.drawable.btn_tab_casehistory_normal,
            label = R.string.tab_biz,
            modifier = Modifier
                .weight(1f)
                .clickable {
                    onTabSelected.invoke(1)
                },
            tint = if (selectedIndex == 1) Color.Blue else Color.Black
        )
        TabItem(
            icon = if (selectedIndex == 2) R.drawable.btn_tab_mine_pressed else R.drawable.btn_tab_mine_normal,
            label = R.string.tab_me,
            modifier = Modifier
                .weight(1f)
                .clickable {
                    onTabSelected.invoke(2)
                },
            tint = if (selectedIndex == 2) Color.Blue else Color.Black
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BottomBarPreview() {
    ComposeTheme {
        BottomBar()
    }
}

@Preview(showBackground = true)
@Composable
fun BottomBarPreviewDark() {
    ComposeTheme(darkTheme = true) {
        Surface() {
            BottomBar()
        }
    }
}

@Composable
fun TabItem(
    @DrawableRes icon: Int,
    @StringRes label: Int,
    tint: Color,
    modifier: Modifier
) {
    Column(
        modifier = modifier.padding(top = 4.dp, bottom = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = stringResource(label),
            modifier = Modifier.size(32.dp),
            tint = tint,
        )
        Text(text = stringResource(label), fontSize = 12.sp, color = tint)
    }
}

@Preview(showBackground = true)
@Composable
fun TabItemPreview() {
    ComposeTheme {
        TabItem(
            icon = R.drawable.btn_tab_inquiry_normal,
            label = R.string.tab_home,
            tint = Color.Black,
            modifier = Modifier
        )
    }
}
