using ReactNative.Bridge;
using System;
using System.Collections.Generic;
using Windows.ApplicationModel.Core;
using Windows.UI.Core;

namespace Fillr.Rn.Wrapper.RNFillrRnWrapper
{
    /// <summary>
    /// A module that allows JS to share data.
    /// </summary>
    class RNFillrRnWrapperModule : NativeModuleBase
    {
        /// <summary>
        /// Instantiates the <see cref="RNFillrRnWrapperModule"/>.
        /// </summary>
        internal RNFillrRnWrapperModule()
        {

        }

        /// <summary>
        /// The name of the native module.
        /// </summary>
        public override string Name
        {
            get
            {
                return "RNFillrRnWrapper";
            }
        }
    }
}
