package altline.foodspo.ui.explore

import altline.foodspo.ui.core.LocalNavController
import altline.foodspo.ui.core.component.InfoPanel
import altline.foodspo.ui.core.component.LoadingSpinner
import altline.foodspo.ui.core.component.RecipeCard
import altline.foodspo.ui.core.component.RecipeCardUi
import altline.foodspo.ui.placeholder.PlaceholderImages
import altline.foodspo.ui.theme.AppTheme
import altline.foodspo.util.anyError
import altline.foodspo.util.isAnyLoading
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import kotlinx.coroutines.flow.flowOf
import org.koin.androidx.compose.koinViewModel

@Composable
fun ExploreScreen(viewModel: ExploreViewModel = koinViewModel()) {
    val appNavController = LocalNavController.current
    
    Content(
        pagedRecipes = viewModel.uiState.recipes.collectAsLazyPagingItems(),
        onRecipeClick = appNavController::navigateToRecipeDetails,
        onAddToShoppingList = viewModel::addIngredientsToShoppingList,
        onToggleSave = viewModel::toggleSaveRecipe
    )
}

@Composable
private fun Content(
    pagedRecipes: LazyPagingItems<RecipeCardUi>,
    onRecipeClick: (recipeId: Long) -> Unit,
    onAddToShoppingList: (recipeId: Long) -> Unit,
    onToggleSave: (recipeId: Long) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(AppTheme.spaces.xl),
        verticalArrangement = Arrangement.spacedBy(AppTheme.spaces.xl)
    ) {
        items(pagedRecipes) { recipe ->
            recipe?.let {
                RecipeCard(
                    recipe = recipe,
                    onRecipeClick = { onRecipeClick(recipe.id) },
                    onAddToShoppingList = { onAddToShoppingList(recipe.id) },
                    onToggleSave = { onToggleSave(recipe.id) }
                )
            }
        }
        item {
            pagedRecipes.loadState.anyError?.let {
                InfoPanel(it) {
                    pagedRecipes.retry()
                }
            }
            
            if (pagedRecipes.loadState.isAnyLoading) {
                LoadingSpinner()
            }
        }
    }
}


@Preview
@Composable
fun PreviewContent() {
    AppTheme {
        Content(
            flowOf(
                PagingData.from(
                    listOf(
                        RecipeCardUi(
                            id = 0,
                            title = "Pasta with Garlic, Scallions, Cauliflower & Breadcrumbs",
                            image = PlaceholderImages.recipe,
                            author = "Maplewood Road",
                            isSaved = false
                        ),
                        RecipeCardUi(
                            id = 1,
                            title = "Spaghetti with Meatballs",
                            image = PlaceholderImages.recipe,
                            author = "Maplewood Road",
                            isSaved = true
                        )
                    )
                )
            ).collectAsLazyPagingItems(),
            onRecipeClick = {},
            onAddToShoppingList = {},
            onToggleSave = {}
        )
    }
}