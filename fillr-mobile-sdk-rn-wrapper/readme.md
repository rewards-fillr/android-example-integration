# Fillr React Native Sample Integration

This project demonstrates Fillr being integrated into a react native environment. 

## Getting Started

In order to get started you will need to setup a react native environment. The project at it's current state only demonstrates integration into an Android application, iOS will follow. We will not be using expo, we will be using react-native-cli. The instructions are operating system specific to OSX. 


### Prerequisites

Presuming you already have brew installed,

	1. brew install node (Node: install version 6.2.2 or greater. Node v8 would be ideal.)
	2. brew install watchman
	3. npm install -g react-native-cli

You can also refer to - https://facebook.github.io/react-native/docs/getting-started.html

### Installing

The project consists of a React Native Library and a Sample Application. 

	1. fillr-rn-wrapper
	2. FillrRNSampleApp

In order to run the application we first need to get the library application running,
	1. Navigate into the folder - `fillr-stack/fillr-mobile-sdk-rn-wrapper/fillr-rn-wrapper/`
	2. Enter `npm install`. This installs the library into the local npm repository.
	3. Linking of the library to the react native application has already been done. Therefore we can skip this step.
	4. Navigate to the sample react native application `fillr-stack/fillr-mobile-sdk-rn-wrapper/FillrRNSampleApp`
	5. Install the node modules `npm install`
	6. Run the project using `react-native run-android`

The library references the Fillr Library project in the form of aar's. If you want the changes you made in the Fillr App to be reflected in the wrapper, you need to run the script located, in `fillr-stack/fillr-android/script/copy_libs_to_react_native_app.sh`. It needs to be run from the `fillr-android` directory. 


## Running the tests

No tests at the moment. 

## Deployment

No deployment of the npm library is required at the moment. It will be linked and used using a local repository. 

## Versioning

The project is still in it's inception and will be used as an internal testing tool. However the `fillr-rn-wrapper` could be used by an integrator, if we choose to expose it.  

