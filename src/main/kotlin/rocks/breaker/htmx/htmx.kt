@file:Suppress("unused")

package rocks.breaker.htmx

import kotlinx.html.HTMLTag

object HxRequestHeader {
    const val HxRequestHeaderBoosted = "HX-Boosted"// Boolean
    const val HxRequestHeaderCurrentURL = "HX-Current-URL"// String
    const val HxRequestHeaderHistoryRestoreRequest = "HX-History-Restore-Request"// Boolean
    const val HxRequestHeaderPrompt = "HX-Prompt"// String
    const val HxRequest = "HX-Request"// Boolean
    const val HxRequestHeaderTarget = "HX-Target"// String
    const val HxRequestHeaderTriggerName = "HX-Trigger-Name"// String
    const val HxRequestHeaderTrigger = "HX-Trigger"// String
}

object HxResponseHeader {
    const val HXLocation = "HX-Location"                       // Allows you to do a client-side redirect that does not do a full page reload
    const val HXPushUrl = "HX-Push-Url"                        // pushes a new url into the history stack
    const val HXRedirect = "HX-Redirect"                       // can be used to do a client-side redirect to a new location
    const val HXRefresh = "HX-Refresh"                         // if set to "true" the client side will do a full refresh of the page
    const val HXReplaceUrl = "HX-Replace-Url"                  // replaces the current URL in the location bar
    const val HXReswap = "HX-Reswap"                           // Allows you to specify how the response will be swapped. See hx-swap for possible values
    const val HXRetarget = "HX-Retarget"                       // A CSS selector that updates the target of the content update to a different element on the page
    const val HXReselect = "HX-Reselect"                       // A CSS selector that allows you to choose which part of the response is used to be swapped in. Overrides an existing hx-select on the triggering element
    const val HXTrigger = "HX-Trigger"                         // allows you to trigger client side events, see the documentation for more info
    const val HXTriggerAfterSettle = "HX-Trigger-After-Settle" // allows you to trigger client side events, see the documentation for more info
    const val HXTriggerAfterSwap = "HX-Trigger-After-Swap"     // allows you to trigger client side events, see the documentation for more info
}

private fun joinModifiers(modifiers: Array<out String>) = modifiers.joinToString(prefix = " ", separator = " ")

/**
 * [hx-get HTMX reference](https://htmx.org/attributes/hx-get/)
 */
fun HTMLTag.hxGet(value: String) {
    attributes["hx-get"] = value
}

/**
 * [hx-post HTMX reference](https://htmx.org/attributes/hx-post/)
 */
fun HTMLTag.hxPost(value: String) {
    attributes["hx-post"] = value
}

/**
 * [hx-put HTMX reference](https://htmx.org/attributes/hx-put/)
 */
fun HTMLTag.hxPut(value: String) {
    attributes["hx-put"] = value
}

/**
 * [hx-delete HTMX reference](https://htmx.org/attributes/hx-delete/)
 */
fun HTMLTag.hxDelete(value: String) {
    attributes["hx-delete"] = value
}

/**
 * [hx-patch HTMX reference](https://htmx.org/attributes/hx-patch/)
 */
fun HTMLTag.hxPatch(value: String) {
    attributes["hx-patch"] = value
}

/**
 * [hx-boost HTMX reference](https://htmx.org/attributes/hx-boost/)
 */
fun HTMLTag.hxBoost(enabled: Boolean = true) {
    attributes["hx-boost"] = "$enabled"
}

/**
 * [hx-confirm HTMX reference](https://htmx.org/attributes/hx-confirm/)
 */
fun HTMLTag.hxConfirm(value: String) {
    attributes["hx-confirm"] = value
}

/**
 * [hx-disable HTMX reference](https://htmx.org/attributes/hx-disable/)
 */
fun HTMLTag.hxDisable() {
    attributes["hx-disable"] = ""
}

/**
 * [hx-disabled-elt HTMX reference](https://htmx.org/attributes/hx-disabled-elt/)
 */
fun HTMLTag.hxDisabledElt(value: String) {
    attributes["hx-disabled-elt"] = value
}

/**
 * [hx-disinherit HTMX reference](https://htmx.org/attributes/hx-disinherit/)
 */
fun HTMLTag.hxDisinherit(value: String) {
    attributes["hx-disinherit"] = value
}

/**
 * [hx-encoding HTMX reference](https://htmx.org/attributes/hx-encoding/)
 */
fun HTMLTag.hxEncoding(value: String) {
    attributes["hx-encoding"] = value
}

/**
 * [hx-ext HTMX reference](https://htmx.org/attributes/hx-ext/)
 */
fun HTMLTag.hxExt(value: String) {
    attributes["hx-ext"] = value
}

/**
 * [hx-headers HTMX reference](https://htmx.org/attributes/hx-headers/)
 */
fun HTMLTag.hxHeaders(value: String) {
    attributes["hx-headers"] = value
}

/**
 * [hx-history HTMX reference](https://htmx.org/attributes/hx-history/)
 */
fun HTMLTag.hxHistory(historyEnabled: Boolean = false) {
    attributes["hx-history"] = "$historyEnabled"
}

/**
 * [hx-history-elt HTMX reference](https://htmx.org/attributes/hx-history-elt/)
 */
fun HTMLTag.hxHistoryElt() {
    attributes["hx-history-eld"] = ""
}

/**
 * [hx-include HTMX reference](https://htmx.org/attributes/hx-include/)
 */
fun HTMLTag.hxInclude(value: String) {
    attributes["hx-include"] = value
}

/**
 * [hx-indicator HTMX reference](https://htmx.org/attributes/hx-indicator/)
 */
fun HTMLTag.hxIndicator(value: String) {
    attributes["hx-indicator"] = value
}

/**
 * [hx-on HTMX reference](https://htmx.org/attributes/hx-on/)
 */
fun HTMLTag.hxOn(event: String, value: String) {
    attributes["hx-on::$event"] = value
}

/**
 * [hx-params HTMX reference](https://htmx.org/attributes/hx-params/)
 */
fun HTMLTag.hxParams(value: String) {
    attributes["hx-params"] = value
}

/**
 * [hx-preserve HTMX reference](https://htmx.org/attributes/hx-preserve/)
 */
fun HTMLTag.hxPreserve() {
    attributes["hx-preserve"] = ""
}

/**
 * [hx-prompt HTMX reference](https://htmx.org/attributes/hx-prompt/)
 */
fun HTMLTag.hxPrompt(value: String) {
    attributes["hx-prompt"] = value
}

/**
 * [hx-replace-url HTMX reference](https://htmx.org/attributes/hx-replace-url/)
 */
fun HTMLTag.hxReplaceUrl(value: String) {
    attributes["hx-replace-url"] = value
}

/**
 * [hx-replace-url HTMX reference](https://htmx.org/attributes/hx-replace-url/)
 */
fun HTMLTag.hxReplaceUrl(enabled: Boolean = true) {
    attributes["hx-replace-url"] = "$enabled"
}

/**
 * [hx-request HTMX reference](https://htmx.org/attributes/hx-request/)
 */
fun HTMLTag.hxRequest(value: String) {
    attributes["hx-request"] = value
}

/**
 * [hx-select HTMX reference](https://htmx.org/attributes/hx-select/)
 */
fun HTMLTag.hxSelect(value: String) {
    attributes["hx-select"] = value
}

/**
 * [hx-select-oob HTMX reference](https://htmx.org/attributes/hx-select-oob/)
 */
fun HTMLTag.hxSelectOob(value: String) {
    attributes["hx-select-oob"] = value
}

/**
 * [hx-swap HTMX reference](https://htmx.org/attributes/hx-swap/)
 */
fun HTMLTag.hxSwapInnerHtml(vararg modifiers: String) {
    attributes["hx-swap"] = "innerHTML${joinModifiers(modifiers)}"
}
fun HTMLTag.hxSwapInnerHtml2(vararg modifiers: String) {
    attributes["hx-swap"] = "innerHTML${joinModifiers(modifiers)}"
}

/**
 * [hx-swap HTMX reference](https://htmx.org/attributes/hx-swap/)
 */
fun HTMLTag.hxSwapOuterHtml(vararg modifiers: String) {
    attributes["hx-swap"] = "outerHTML${joinModifiers(modifiers)}"
}

/**
 * [hx-swap HTMX reference](https://htmx.org/attributes/hx-swap/)
 */
fun HTMLTag.hxSwapBeforeBegin(vararg modifiers: String) {
    attributes["hx-swap"] = "beforebegin${joinModifiers(modifiers)}"
}

/**
 * [hx-swap HTMX reference](https://htmx.org/attributes/hx-swap/)
 */
fun HTMLTag.hxSwapAfterBegin(vararg modifiers: String) {
    attributes["hx-swap"] = "afterbegin${joinModifiers(modifiers)}"
}

/**
 * [hx-swap HTMX reference](https://htmx.org/attributes/hx-swap/)
 */
fun HTMLTag.hxSwapBeforeEnd(vararg modifiers: String) {
    attributes["hx-swap"] = "beforeend${joinModifiers(modifiers)}"
}

/**
 * [hx-swap HTMX reference](https://htmx.org/attributes/hx-swap/)
 */
fun HTMLTag.hxSwapAfterEnd(vararg modifiers: String) {
    attributes["hx-swap"] = "afterend${joinModifiers(modifiers)}"
}

/**
 * [hx-swap HTMX reference](https://htmx.org/attributes/hx-swap/)
 */
fun HTMLTag.hxSwapDelete(vararg modifiers: String) {
    attributes["hx-swap"] = "delete${joinModifiers(modifiers)}"
}

/**
 * [hx-swap HTMX reference](https://htmx.org/attributes/hx-swap/)
 */
fun HTMLTag.hxSwapNone(vararg modifiers: String) {
    attributes["hx-swap"] = "none${joinModifiers(modifiers)}"
}

/**
 * [hx-swap-oob HTMX reference](https://htmx.org/attributes/hx-swap-oob/)
 */
fun HTMLTag.hxSwapOob(value: String) {
    attributes["hx-swap-oob"] = value
}

/**
 * [hx-sync HTMX reference](https://htmx.org/attributes/hx-sync/)
 */
fun HTMLTag.hxSync(value: String) {
    attributes["hx-sync"] = value
}

/**
 * [hx-target HTMX reference](https://htmx.org/attributes/hx-target/)
 */
fun HTMLTag.hxTarget(value: String) {
    attributes["hx-target"] = value
}

/**
 * [hx-vals HTMX reference](https://htmx.org/attributes/hx-vals/)
 */
fun HTMLTag.hxVals(value: String) {
    attributes["hx-vals"] = value
}