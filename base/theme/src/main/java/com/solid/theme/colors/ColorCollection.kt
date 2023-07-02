package com.solid.theme.colors

import androidx.compose.runtime.staticCompositionLocalOf

data class ThemeColor(
    val light: ColorCollection,
    val dark: ColorCollection
)

data class ColorCollection(
    val global: ColorSet,
    val typography: ColorSet,
    val background: ColorSet,
    val divider: ColorSet,
    val positive: ColorSet,
    val negative: ColorSet,
    val warning: ColorSet,
    val links: ColorSet,
    val tabs: ColorSet,
    val tags: ColorSet
)

val defaultCollectionLight = ColorCollection(
    global = DefaultGlobal,
    typography = DefaultTypography,
    background = DefaultBackground,
    divider = DefaultDivider,
    positive = DefaultPositive,
    negative = DefaultNegative,
    warning = DefaultWarning,
    links = DefaultLinks,
    tabs = DefaultTabs,
    tags = DefaultTags
)

val defaultCollectionDark = ColorCollection(
    global = DefaultGlobal,
    typography = DefaultTypographyInv,
    background = DefaultBackgroundInv,
    divider = DefaultDividerInv,
    positive = DefaultPositiveInv,
    negative = DefaultNegativeInv,
    warning = DefaultWarningInv,
    links = DefaultLinksInv,
    tabs = DefaultTabsInv,
    tags = DefaultTagsInv
)

val LocalColors = staticCompositionLocalOf { defaultCollectionLight }