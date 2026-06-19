package dev.stukalo.mealplanner.presentation.core.ui.haze

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.layer.GraphicsLayer
import androidx.compose.ui.graphics.layer.drawLayer
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.node.CompositionLocalConsumerModifierNode
import androidx.compose.ui.node.DrawModifierNode
import androidx.compose.ui.node.GlobalPositionAwareModifierNode
import androidx.compose.ui.node.ModifierNodeElement
import androidx.compose.ui.node.currentValueOf
import androidx.compose.ui.node.invalidateDraw
import androidx.compose.ui.platform.LocalGraphicsContext

/**
 * A simplified version of the Haze blur effect.
 * Captures content from a source and allows drawing it elsewhere.
 */
@Stable
class HazeState {
    var contentLayer: GraphicsLayer? by mutableStateOf(null)
    internal val consumers = mutableStateListOf<HazeChildNode>()
}

@Composable
fun rememberHazeState() = remember { HazeState() }

/**
 * Captures the content of the composable it is applied to into a GraphicsLayer.
 */
fun Modifier.hazeSource(state: HazeState): Modifier = this
    .then(HazeSourceElement(state))

private data class HazeSourceElement(val state: HazeState) : ModifierNodeElement<HazeSourceNode>() {
    override fun create() = HazeSourceNode(state)
    override fun update(node: HazeSourceNode) {
        node.state = state
    }
}

class HazeSourceNode(var state: HazeState) : Modifier.Node(), DrawModifierNode, CompositionLocalConsumerModifierNode {
    override fun ContentDrawScope.draw() {
        val graphicsContext = currentValueOf(LocalGraphicsContext)
        val layer = state.contentLayer ?: graphicsContext.createGraphicsLayer().also { state.contentLayer = it }
        layer.record {
            this@draw.drawContent()
        }
        drawLayer(layer)
        
        // Notify all consumers to redraw themselves to match the updated background content
        state.consumers.forEach { it.invalidateDraw() }
    }
}

/**
 * Draws the captured background content. 
 * This modifier does NOT draw the composable's own content, 
 * making it suitable for use with Modifier.blur() in a separate background layer.
 */
fun Modifier.hazeChild(
    state: HazeState,
    tint: Color = Color.Transparent
): Modifier = this.then(HazeChildElement(state, tint))

private data class HazeChildElement(val state: HazeState, val tint: Color) : ModifierNodeElement<HazeChildNode>() {
    override fun create() = HazeChildNode(state, tint)
    override fun update(node: HazeChildNode) {
        node.state = state
        node.tint = tint
    }
}

class HazeChildNode(var state: HazeState, var tint: Color) :
    Modifier.Node(), DrawModifierNode, GlobalPositionAwareModifierNode, CompositionLocalConsumerModifierNode {
    
    private var position by mutableStateOf(Offset.Zero)

    override fun onAttach() {
        state.consumers.add(this)
    }

    override fun onDetach() {
        state.consumers.remove(this)
    }

    override fun onGloballyPositioned(coordinates: LayoutCoordinates) {
        position = coordinates.positionInRoot()
    }

    override fun ContentDrawScope.draw() {
        state.contentLayer?.let { layer ->
            translate(-position.x, -position.y) {
                drawLayer(layer)
            }
        }
        if (tint != Color.Transparent) {
            drawRect(color = tint)
        }
    }
}
