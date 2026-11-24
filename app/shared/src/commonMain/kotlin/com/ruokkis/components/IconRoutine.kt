package com.ruokkis.components

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

@Suppress("CheckReturnValue")
public val routine: ImageVector
    get() {
        if (_routine != null) {
            return _routine!!
        }
        _routine =
            ImageVector.Builder(
                name = "routine",
                defaultWidth = 24.dp,
                defaultHeight = 24.dp,
                viewportWidth = 24f,
                viewportHeight = 24f,
            )
                .apply {
                    path(
                        fill = SolidColor(Color.Black),
                        fillAlpha = 1f,
                        stroke = null,
                        strokeAlpha = 1f,
                        strokeLineWidth = 1f,
                        strokeLineCap = StrokeCap.Butt,
                        strokeLineJoin = StrokeJoin.Bevel,
                        strokeLineMiter = 1f,
                        pathFillType = PathFillType.Companion.NonZero,
                    ) {
                        moveTo(8.44f, 12.43f)
                        quadTo(7.78f, 11.55f, 7.23f, 10.58f)
                        quadTo(7.1f, 10.93f, 7.06f, 11.29f)
                        reflectiveQuadTo(7.03f, 12f)
                        quadToRelative(0f, 2.07f, 1.45f, 3.53f)
                        quadTo(9.93f, 16.98f, 12f, 16.98f)
                        quadToRelative(0.35f, 0f, 0.71f, -0.05f)
                        reflectiveQuadToRelative(0.71f, -0.15f)
                        quadTo(12.45f, 16.23f, 11.58f, 15.56f)
                        reflectiveQuadTo(9.9f, 14.1f)
                        reflectiveQuadTo(8.44f, 12.43f)
                        close()
                        moveToRelative(5.74f, 2.46f)
                        quadToRelative(1.57f, 0.91f, 3.38f, 1.41f)
                        quadToRelative(-1f, 1.27f, -2.45f, 1.99f)
                        reflectiveQuadTo(12.03f, 19f)
                        quadTo(9.1f, 19f, 7.06f, 16.96f)
                        quadTo(5.03f, 14.93f, 5.03f, 12f)
                        quadToRelative(0f, -1.63f, 0.71f, -3.08f)
                        reflectiveQuadTo(7.73f, 6.47f)
                        quadToRelative(0.5f, 1.8f, 1.41f, 3.38f)
                        reflectiveQuadToRelative(2.19f, 2.85f)
                        reflectiveQuadToRelative(2.85f, 2.19f)
                        close()
                        moveToRelative(4.4f, -0.39f)
                        quadToRelative(-0.5f, -0.13f, -0.99f, -0.28f)
                        reflectiveQuadTo(16.63f, 13.88f)
                        quadToRelative(0.2f, -0.45f, 0.29f, -0.91f)
                        reflectiveQuadTo(17f, 12f)
                        quadTo(17f, 9.92f, 15.54f, 8.46f)
                        reflectiveQuadTo(12f, 7f)
                        quadTo(11.5f, 7f, 11.04f, 7.09f)
                        quadTo(10.58f, 7.18f, 10.13f, 7.38f)
                        quadTo(9.93f, 6.9f, 9.79f, 6.43f)
                        reflectiveQuadTo(9.53f, 5.45f)
                        quadToRelative(0.6f, -0.23f, 1.22f, -0.34f)
                        reflectiveQuadTo(12.03f, 5f)
                        quadToRelative(2.93f, 0f, 4.96f, 2.04f)
                        quadTo(19.03f, 9.07f, 19.03f, 12f)
                        quadToRelative(0f, 0.65f, -0.11f, 1.27f)
                        reflectiveQuadTo(18.58f, 14.5f)
                        close()
                        moveTo(11f, 3f)
                        verticalLineTo(0f)
                        horizontalLineToRelative(2f)
                        verticalLineTo(3f)
                        horizontalLineTo(11f)
                        close()
                        moveToRelative(0f, 21f)
                        verticalLineTo(21f)
                        horizontalLineToRelative(2f)
                        verticalLineToRelative(3f)
                        horizontalLineTo(11f)
                        close()
                        moveTo(19.08f, 6.35f)
                        lineTo(17.65f, 4.93f)
                        lineToRelative(2.13f, -2.1f)
                        lineToRelative(1.43f, 1.4f)
                        lineTo(19.08f, 6.35f)
                        close()
                        moveTo(4.23f, 21.18f)
                        lineTo(2.8f, 19.77f)
                        lineTo(4.93f, 17.65f)
                        lineToRelative(1.42f, 1.43f)
                        lineToRelative(-2.13f, 2.1f)
                        close()
                        moveTo(21f, 13f)
                        verticalLineTo(11f)
                        horizontalLineToRelative(3f)
                        verticalLineToRelative(2f)
                        horizontalLineTo(21f)
                        close()
                        moveTo(0f, 13f)
                        verticalLineTo(11f)
                        horizontalLineTo(3f)
                        verticalLineToRelative(2f)
                        horizontalLineTo(0f)
                        close()
                        moveToRelative(19.78f, 8.2f)
                        lineTo(17.65f, 19.08f)
                        lineToRelative(1.42f, -1.43f)
                        lineToRelative(2.1f, 2.13f)
                        lineToRelative(-1.4f, 1.43f)
                        close()
                        moveTo(4.93f, 6.35f)
                        lineTo(2.83f, 4.22f)
                        lineTo(4.23f, 2.8f)
                        lineTo(6.35f, 4.93f)
                        lineTo(4.93f, 6.35f)
                        close()
                        moveTo(9.9f, 14.1f)
                        close()
                    }
                }
                .build()
        return _routine!!
    }

private var _routine: ImageVector? = null