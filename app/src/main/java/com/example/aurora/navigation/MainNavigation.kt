package com.example.aurora.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.aurora.features.dispenser.AddScheduleScreen
import com.example.aurora.features.dispenser.ContainerScreen
import com.example.aurora.features.dispenser.ContainerViewModel
import com.example.aurora.features.dispenser.DispenserScreen
import com.example.aurora.features.dispenser.DispenserViewModel
import com.example.aurora.features.forgotPassword.ForgotPasswordScreen
import com.example.aurora.features.forgotPassword.ForgotViewModel
import com.example.aurora.features.google.GoogleScreen
import com.example.aurora.features.home.AddDispenserScreen
import com.example.aurora.features.home.AddDispenserViewModel
import com.example.aurora.features.home.HomeScreen
import com.example.aurora.features.home.HomeViewModel
import com.example.aurora.features.dispenser.ScheduleScreen
import com.example.aurora.features.login.LoginScreen
import com.example.aurora.features.login.LoginViewModel
import com.example.aurora.features.profile.PersonalInfoScreen
import com.example.aurora.features.profile.PersonalInformationViewModel
import com.example.aurora.features.profile.ProfileScreen
import com.example.aurora.features.profile.ProfileViewModel
import com.example.aurora.features.settings.SettingsScreen
import com.example.aurora.features.signup.SignupScreen
import com.example.aurora.features.signup.SignupViewModel
import com.example.aurora.navigation.Routes.MainRoute.AddDispenser.toAddDispenser
import com.example.aurora.navigation.Routes.MainRoute.AddSchedule.toAddSchedule
import com.example.aurora.navigation.Routes.MainRoute.Container.toContainer
import com.example.aurora.navigation.Routes.MainRoute.Dispenser.toDispenser
import com.example.aurora.navigation.Routes.MainRoute.Login.toLogIn
import com.example.aurora.navigation.Routes.MainRoute.ForgotPassword.toForgotPassword
import com.example.aurora.navigation.Routes.MainRoute.Google.toGoogle
import com.example.aurora.navigation.Routes.MainRoute.Home.toHome
import com.example.aurora.navigation.Routes.MainRoute.PersonalInformation.toPersonalInformation
import com.example.aurora.navigation.Routes.MainRoute.Profile.toProfile
import com.example.aurora.navigation.Routes.MainRoute.Schedule.toSchedule
import com.example.aurora.navigation.Routes.MainRoute.Settings.toSettings
import com.example.aurora.navigation.Routes.MainRoute.SignUp.toSignUp
import org.koin.androidx.compose.getViewModel

@Composable
fun MainNavigation() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = Routes.MainRoute.Login.route) {
        composable(route = Routes.MainRoute.Login.route) {
            val viewModelLogin = getViewModel<LoginViewModel>()
            val loginData by viewModelLogin.login.collectAsStateWithLifecycle()
            LoginScreen(
                loginData,
                onEmailChange = { viewModelLogin.email(it) },
                onPasswordChange = { viewModelLogin.password(it) },
                onLoginClick = { viewModelLogin.validate() },
                isLoginSuccessful = {
                    Log.d("TAG", "to home ${loginData.email}")
                    viewModelLogin.resetLogin()
                    navController.toHome(loginData.firstName)
                },
                onForgotPasswordClick = { navController.toForgotPassword() },
                onSignUpClick = { navController.toSignUp() },
                onGoogleClick =  { navController.toGoogle()}
            )
        }
        composable(route = Routes.MainRoute.ForgotPassword.route) {
            val viewModelPass = getViewModel<ForgotViewModel>()
            val data by viewModelPass.data.collectAsStateWithLifecycle()
            ForgotPasswordScreen(
                navController,
                data,
                onEmailChange = {viewModelPass.email(it)},
                onPasswordChange = {viewModelPass.password(it)},
                onRepeatChange = {viewModelPass.repeat(it)},
                onSendClick = {viewModelPass.validateEmail()},
                onResetClick = {viewModelPass.validatePassword()},
                isResetSuccessful = {},
                onBackClick = {navController.toLogIn()},
            )
        }
        composable(route = Routes.MainRoute.SignUp.route) {
            val viewModelSignup = getViewModel<SignupViewModel>()
            val signupData by viewModelSignup.signup.collectAsStateWithLifecycle()
            SignupScreen(
                navController,
                signupData,
                onEmailChange = { viewModelSignup.email(it) },
                onPasswordChange = { viewModelSignup.password(it) },
                onFirstNameChange = { viewModelSignup.firstName(it) },
                onLastNameChange = { viewModelSignup.lastName(it) },
                onContinueClick = { viewModelSignup.validateFirstStep() },
                isSignupSuccessful = { viewModelSignup.resetSignup() },
                onCreateAccountClick = {
                    Log.d("TAG", "to home")
                    viewModelSignup.resetSignup()
                    viewModelSignup.validateSecondStep()
                    navController.toHome(signupData.firstName)

                },
                onBackClick = { viewModelSignup.onBackClick() },
                onSecondPasswordChange = { viewModelSignup.passwordRepeat(it) },
                onOneClick = {},
                onTwoClick = {},
            )
        }
        composable(route = Routes.MainRoute.Home.route) { navBackStackEntry ->
            val name = navBackStackEntry.arguments?.getString("name")
            val viewModel = getViewModel<HomeViewModel>()
            val dispensers by viewModel.dispensers.collectAsStateWithLifecycle()
            
            LaunchedEffect(Unit) {
                viewModel.listDispensers()
            }
            
            HomeScreen(
                onToProfileClick = { navController.toProfile() },
                name = name.orEmpty(),
                onAddDispenserClick = { navController.toAddDispenser() },
                onToDispenserClick = { dispenserId, dispenserName -> navController.toDispenser(dispenserId, dispenserName) },
                dispensers = dispensers,
            )
        }
        composable(route = Routes.MainRoute.AddDispenser.route) {
            val viewModelAddDispenser = getViewModel<AddDispenserViewModel>()
            val dispenserData by viewModelAddDispenser.dispenser.collectAsStateWithLifecycle()
            LaunchedEffect(Unit) {
                viewModelAddDispenser.fetchDispenserNames()
            }
            AddDispenserScreen(
                dispenser = dispenserData,
                onIDChange = { viewModelAddDispenser.id(it) },
                onNameChange = { viewModelAddDispenser.name(it) },
                onAddDispenserClick = { viewModelAddDispenser.validate() },
                isAddDispenserSuccessful = {
                    Log.d("TAG", "add dispenser")
                    viewModelAddDispenser.resetAdd()
                    navController.navigateUp()
                },
                onBackClick = { navController.navigateUp() }
            )
        }
        composable(route = Routes.MainRoute.Dispenser.route) { navBackStackEntry ->
            val dispenserId = navBackStackEntry.arguments?.getString("id") ?: ""
            val dispenserName = navBackStackEntry.arguments?.getString("name") ?: ""
            val dispenserViewModel = getViewModel<DispenserViewModel>()
            val dispenser by dispenserViewModel.dispenser.collectAsStateWithLifecycle()
            val showHideRename by dispenserViewModel.showPopUpRename.collectAsStateWithLifecycle()
            val showHideDelete by dispenserViewModel.showPopUpDelete.collectAsStateWithLifecycle()
            LaunchedEffect(dispenserId) {
                dispenserViewModel.loadDispenser(dispenserId)
            }
            DispenserScreen(
                dispenser = dispenser,
                showHideRename = showHideRename,
                showHideDelete = showHideDelete,
                onBackClick = { navController.navigateUp() },
                onPillClick = { slot, pillName, containerId ->
                    navController.toContainer(dispenserId, dispenserName, slot, pillName, containerId)
                },
                onDeleteClick = {
                    dispenserViewModel.deleteDispenser(
                        dispenserName,
                        onSuccess = { navController.navigateUp() }
                    )
                },
                onRenameChange = { dispenserViewModel.setRenameDraft(it) },
                onRenameConfirm = { dispenserViewModel.confirmRename() },
                isRenameSuccessful = { dispenserViewModel.resetRename() },
                onBackToDispenserRenameClicked = { dispenserViewModel.showHideRenameBack()},
                onBackToDispenserDeleteClicked = { dispenserViewModel.showHideDeleteBack()},
            )
        }
        composable(route = Routes.MainRoute.Container.route){ navBackStackEntry ->
            val dispenserName = navBackStackEntry.arguments?.getString("dispenserName") ?: ""
            val slot = navBackStackEntry.arguments?.getString("slot")?.toIntOrNull() ?: 0
            val pillName = navBackStackEntry.arguments?.getString("pillName") ?: ""
            val containerId = navBackStackEntry.arguments?.getString("containerId") ?.toIntOrNull() ?: 0
            val containerViewModel = getViewModel<ContainerViewModel>()
            val container by containerViewModel.container.collectAsStateWithLifecycle()
            val showHideRename by containerViewModel.showPopUpRename.collectAsStateWithLifecycle()
            LaunchedEffect(dispenserName, slot, pillName, containerId) {
                containerViewModel.setBaseInfo(dispenserName, slot, pillName, containerId)
                if (containerId != 0) {
                    containerViewModel.listSchedules()
                }
            }
            ContainerScreen(
                container = container,
                showHideRename = showHideRename,
                onBackClick = { navController.navigateUp() },
                onScheduleClick = { navController.toSchedule() },
                onAddScheduleClick = { navController.toAddSchedule(containerId) },
                onRenameChange = { containerViewModel.setRenameDraft(it) },
                onRenameConfirm = { containerViewModel.confirmRename() },
                onBackToContainerRenameClicked = { containerViewModel.showHideRenameBack() },
                isRenameSuccessful = { containerViewModel.resetRename() },
            )
        }
        composable(route = Routes.MainRoute.AddSchedule.route){ navBackStackEntry ->
            val containerId = navBackStackEntry.arguments?.getString("containerId")?.toIntOrNull() ?: 0
            val addScheduleViewModel = getViewModel<com.example.aurora.features.dispenser.AddScheduleViewModel>()
            val formState by addScheduleViewModel.state.collectAsStateWithLifecycle()
            LaunchedEffect(containerId) {
                addScheduleViewModel.resetSuccess()
                addScheduleViewModel.loadSchedules(containerId)
            }
            AddScheduleScreen(
                state = formState,
                onDayChange = { addScheduleViewModel.onDayChange(it) },
                onHourChange = { addScheduleViewModel.onHourChange(it) },
                onMinuteChange = { addScheduleViewModel.onMinuteChange(it) },
                onRepeatChange = { addScheduleViewModel.onRepeatChange(it) },
                onSave = { addScheduleViewModel.save(containerId) },
                onBackClick = { navController.navigateUp() }
            )
        }
        composable(route = Routes.MainRoute.Schedule.route){
            ScheduleScreen()
        }
        composable(route = Routes.MainRoute.Google.route) {
            GoogleScreen()
        }
        composable(route = Routes.MainRoute.Settings.route) {
            SettingsScreen()
        }
        composable(route = Routes.MainRoute.Profile.route) {
            val viewModel = getViewModel<ProfileViewModel>()
            val showHideLogOut by viewModel.showPopUpLogOut.collectAsStateWithLifecycle()
            val showHideDelete by viewModel.showPopUpDelete.collectAsStateWithLifecycle()
            val user by viewModel.personalInfo.collectAsStateWithLifecycle()
            LaunchedEffect(true) {
                viewModel.getUser()
            }
            ProfileScreen(
                showPopupLogOut = showHideLogOut,
                showPopupDelete = showHideDelete,
                onLogOutClicked = { viewModel.performLogout { navController.toLogIn() } },
                onDeleteAccountClicked = { viewModel.performDelete { navController.toSignUp()  }},
                onPersonalInformation = { navController.toPersonalInformation() },
                onBackToProfileLogClicked = { viewModel.showHideLogOutBack() },  //hide popup
                onBackToProfileDeleteClicked = { viewModel.showHideDeleteBack()},  //hide popup
                onSettings = { navController.toSettings() },
                onLogOut = { viewModel.showHideLogOutBack() }, // show popup
                onDeleteAccount = { viewModel.showHideDeleteBack() }, //show popup
                onToHomeClick = {navController.toHome(user.firstName)},
                personalInfo = user,
            )
        }
        composable(route = Routes.MainRoute.PersonalInformation.route) {
            val viewModel = getViewModel<PersonalInformationViewModel>()
            val personalInformationData by viewModel.personalInformation.collectAsStateWithLifecycle()

            PersonalInfoScreen(
                personalInformationData = personalInformationData,
                onLastNameChange = { viewModel.lastName(it) },
                onFirstNameChange = { viewModel.firstName(it) },
                onBackClick = { navController.navigateUp() },
                onUpdateNamesClick = { viewModel.validateNames() },
                isUpdateNamesSuccessful = {
                    Log.d("TAG", "to profile")
                    viewModel.resetUpdateNames()
                    navController.toProfile()
                },
            )
        }
    }
}