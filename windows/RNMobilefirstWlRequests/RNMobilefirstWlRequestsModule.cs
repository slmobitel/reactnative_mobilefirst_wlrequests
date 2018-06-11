using ReactNative.Bridge;
using System;
using System.Collections.Generic;
using Windows.ApplicationModel.Core;
using Windows.UI.Core;

namespace Mobilefirst.Wl.Requests.RNMobilefirstWlRequests
{
    /// <summary>
    /// A module that allows JS to share data.
    /// </summary>
    class RNMobilefirstWlRequestsModule : NativeModuleBase
    {
        /// <summary>
        /// Instantiates the <see cref="RNMobilefirstWlRequestsModule"/>.
        /// </summary>
        internal RNMobilefirstWlRequestsModule()
        {

        }

        /// <summary>
        /// The name of the native module.
        /// </summary>
        public override string Name
        {
            get
            {
                return "RNMobilefirstWlRequests";
            }
        }
    }
}
