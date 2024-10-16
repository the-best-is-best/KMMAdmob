package io.github.kadmob

import platform.UIKit.UIApplication
import platform.UIKit.UIViewController


fun getCurrentUIViewController(): UIViewController {
    val window = UIApplication.sharedApplication.keyWindow!!
    var viewController = window.rootViewController
    while (viewController?.presentedViewController != null) {
        viewController = viewController.presentedViewController
    }
    return viewController!!
}