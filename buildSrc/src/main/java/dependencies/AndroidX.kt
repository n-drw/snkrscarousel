package dependencies

object AndroidX {
    const val core_ktx = "androidx.core:core-ktx:${Versions.core_ktx}"
    const val app_compat = "androidx.appcompat:appcompat:${Versions.app_compat}"
    const val ui_tooling = "androidx.ui:ui-tooling:${Versions.androidx_ui}"
    const val androidx_fragment = "androidx.fragment:fragment-ktx:${Versions.fragment}"

    const val constraint_compose = "androidx.constraintlayout:constraintlayout-compose:${Versions.compose_constraint}"
    const val compose_activity = "androidx.activity:activity-compose:${Versions.compose_activity}"
    const val ktx_activity = "androidx.activity:activity-ktx:${Versions.activity_ktx}"
    const val compose_ui = "androidx.compose.ui:ui:${Versions.compose}"
    const val compose_ui_tooling = "androidx.compose.ui:ui-tooling:${Versions.compose}"
    const val compose_ui_util = "androidx.compose.ui:ui-util:${Versions.compose}"
    const val compose_foundation = "androidx.compose.foundation:foundation:${Versions.compose}"
    const val compose_material = "androidx.compose.material:material:${Versions.compose}"
    const val compose_icons_core = "androidx.compose.material:material-icons-core:${Versions.compose}"
    const val compose_icons_extended = "androidx.compose.material:material-icons-extended:${Versions.compose}"

    const val navigation_compose = "androidx.navigation:navigation-compose:${Versions.nav_compose}"
    const val navigation_hilt = "androidx.hilt:hilt-navigation:${Versions.hilt_navigation}"
    const val nav_fragment_ktx = "androidx.navigation:navigation-fragment:${Versions.nav_version}"
    const val nav_ui_ktx = "androidx.navigation:navigation-ui:${Versions.nav_version}"

    const val room_runtime = "androidx.room:room-runtime:${Versions.room}"
    const val room_ktx = "androidx.room:room-ktx:${Versions.room}"

    const val datastore = "androidx.datastore:datastore-preferences:${Versions.datastore}"

    const val hilt_lifecycle_viewmodel ="androidx.hilt:hilt-lifecycle-viewmodel:${Versions.hilt_lifecycle_viewmodel}"
}
