package com.example.complocaldemo

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.complocaldemo.ui.theme.CompLocalDemoTheme

//이번 프로젝트에서는 기기의 라이트 모드와 다크 모드에 따라 변경되는 color상태를 선언하고,
//이를 이용해 Composable8의 텍스트 컴포넌트 배경 색상을 제어하는 것이 목표다.
//이 값은 주기적으로 변경되지 않으므로
//staticCompositionLocalOf()함수를 이용할 수 있다.
//val LocalColor = staticCompositionLocalOf { Color.Red }
val LocalColor = staticCompositionLocalOf { Color(0xFFffdbcf) }

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CompLocalDemoTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    showComponents()
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun showComponents() {
    CompLocalDemoTheme {
        //각 Composable tree 가 깊이가 늘어날 경우
        //값을 전달하는 오버로드를 줄이기 위해서
        //CompositionLocal 을 사용한다.
        //ProvidableCompositionLocal 을 생성해야 하는데
        //compositionLocalOf 또는 staticCompositionLocalOf 를 사용해
        // 객체를 반환받아 사용한다.
        // staticCompositionLocalOf() 함수는 자주 변경되지 않는 상탯값을 저장할 때 이용하는 것이 좋다.
        // 상탯값이 변경되면 해당 상태가 할당된 노드의 하위 노드를 모두 재구성해야 하기 때문이다.
        // 한편 compositionLocalOf() 함수는 현재 상태에 접근하는 컴포저블, 즉 하위노드를 제외하고
        //재구성을 수행한다.
        Composable1()
    }
}

@Composable
fun Composable1() {
    var color = if (isSystemInDarkTheme())
        Color(0xFFa08d87)
    else
        Color(0xFFffdbcf)

    Column {
        Composable2()

        CompositionLocalProvider(LocalColor provides color) {
            Composable3()
        }
    }
}

@Composable
fun Composable2() {
    Composable4()
}

@Composable
fun Composable3() {
    Text("Composable 3", modifier = Modifier.background(LocalColor.current))

    CompositionLocalProvider(LocalColor provides Color.Red) {
        Composable5()
    }
}

@Composable
fun Composable4() {
    Composable6()
}


@Composable
fun Composable5() {

    Text("Composable 5", modifier = Modifier.background(LocalColor.current))

    CompositionLocalProvider(LocalColor provides Color.Green) {
        Composable7()
    }
    CompositionLocalProvider(LocalColor provides Color.Yellow) {
        Composable8()
    }
}

@Composable
fun Composable6() {
    Text("Composable 6", modifier = Modifier.background(LocalColor.current))
}

@Composable
fun Composable7() {
    Text("Composable 7", modifier = Modifier.background(LocalColor.current))
}

@Composable
fun Composable8() {
    Text("Composable 8", modifier = Modifier.background(LocalColor.current))
}

//이번 장에서는 CompositionLocal을 소개하고, 레이아웃 계층을 따라 상탯값을 아래로
//전달하지 않고도 접근 가능 상태를 선언하는 방법을 살펴봤다. 이런 방식으로 선언된 상태는
//해당 값이 할당된 계층 트리의 브랜치에만 (국지적으로) 영향을 준다.