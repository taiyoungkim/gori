import SwiftUI
import ComposeApp

@main
struct iOSApp: App {
    init() {
        KoinKt.doInitKoin(platformSpecificModules: [])
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
