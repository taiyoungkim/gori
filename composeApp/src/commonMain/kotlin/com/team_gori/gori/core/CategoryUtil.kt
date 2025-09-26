package com.team_gori.gori.core

import com.team_gori.gori.core.model.Category
import com.team_gori.gori.designsystem.component.ChipItemUi
import gori.composeapp.generated.resources.Res
import gori.composeapp.generated.resources.ic_health_icon
import gori.composeapp.generated.resources.ic_food_icon
import gori.composeapp.generated.resources.ic_travel_icon
import org.jetbrains.compose.resources.DrawableResource

class CategoryUtil {
    private fun categoryIconRes(cat: Category): DrawableResource = when (cat) {
        Category.HEALTH  -> Res.drawable.ic_health_icon
        Category.FOOD    -> Res.drawable.ic_food_icon
        Category.HOBBY   -> Res.drawable.ic_travel_icon
        Category.TRAVEL  -> Res.drawable.ic_travel_icon
        Category.FINANCE -> Res.drawable.ic_travel_icon
        Category.DAILY   -> Res.drawable.ic_travel_icon
        Category.OTHER   -> Res.drawable.ic_travel_icon
    }

    fun Category.toChipItem(): ChipItemUi =
        ChipItemUi(id = name, label = value, icon = categoryIconRes(this))

    fun buildMeetingChipItems(): List<ChipItemUi> =
        listOf(ChipItemUi(id = null, label = "전체", icon = null)) +
                Category.entries.map { it.toChipItem() }
}