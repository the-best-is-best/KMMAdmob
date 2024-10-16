import UIKit
import ComposeApp
import GoogleMobileAds

@main
class AppDelegate: UIResponder, UIApplicationDelegate {
    var window: UIWindow?

    func application(
        _ application: UIApplication,
        didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?
    ) -> Bool {
        window = UIWindow(frame: UIScreen.main.bounds)
        GADMobileAds.sharedInstance().start(completionHandler: nil)

        
        GADMobileAds.sharedInstance().requestConfiguration.testDeviceIdentifiers =
            [ "E7142292-B7E2-4673-8800-085E8B35E783" ] // Sample device ID
        if let window = window {
            window.rootViewController = MainKt.MainViewController()
            window.makeKeyAndVisible()
        }

        return true
    }
}
