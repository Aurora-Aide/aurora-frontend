package com.example.aurora.navigation

import android.app.Activity
import android.util.Log
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.aurora.features.dispenser.AddScheduleScreen
import com.example.aurora.features.dispenser.AddScheduleViewModel
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
import com.example.aurora.features.admin.AdminHomeScreen
import com.example.aurora.features.admin.AdminHomeViewModel
import com.example.aurora.features.admin.AdminCreateDispenserModelScreen
import com.example.aurora.features.admin.AdminCreateDispenserModelViewModel
import com.example.aurora.features.dispenser.ScheduleViewModel
import com.example.aurora.navigation.Routes.MainRoute.AdminHome.toAdminHome
import com.example.aurora.navigation.Routes.MainRoute.AdminCreateDispenserModel.toAdminCreateDispenserModel
import com.example.aurora.navigation.Routes.MainRoute.AddDispenser.toAddDispenser
import com.example.aurora.navigation.Routes.MainRoute.AddSchedule.toAddSchedule
import com.example.aurora.navigation.Routes.MainRoute.Container.toContainer
import com.example.aurora.navigation.Routes.MainRoute.Dispenser.toDispenser
import com.example.aurora.navigation.Routes.MainRoute.ForgotPassword.toForgotPassword
import com.example.aurora.navigation.Routes.MainRoute.Google.toGoogle
import com.example.aurora.navigation.Routes.MainRoute.Home.toHome
import com.example.aurora.navigation.Routes.MainRoute.PersonalInformation.toPersonalInformation
import com.example.aurora.navigation.Routes.MainRoute.Profile.toProfile
import com.example.aurora.navigation.Routes.MainRoute.Schedule.toSchedule
import com.example.aurora.navigation.Routes.MainRoute.Settings.toSettings
import com.example.aurora.navigation.Routes.MainRoute.SignUp.toSignUp
import kotlinx.coroutines.delay
import org.koin.androidx.compose.getViewModel

@Composable
fun MainNavigation() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val activity = context as? Activity
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val doubleBackRoutes = setOf(
        Routes.MainRoute.Login.route,
        Routes.MainRoute.SignUp.route,
        Routes.MainRoute.Home.route,
        Routes.MainRoute.AdminHome.route
    )

    val isDoubleBackScreen = currentRoute in doubleBackRoutes
    var backPressedOnce by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(backPressedOnce) {
        if (backPressedOnce) {
            delay(1500)
            backPressedOnce = false
        }
    }
    BackHandler(enabled = isDoubleBackScreen) {
        if (backPressedOnce) {
            activity?.moveTaskToBack(true)
        } else {
            backPressedOnce = true
            Toast.makeText(context, "Press back again to exit", Toast.LENGTH_SHORT).show()
        }
    }

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
                    val name = loginData.firstName
                    if (loginData.isAdmin) {
                        navController.navigate("${Routes.MainRoute.AdminHome.route.replace("{name}", Uri.encode(name))}") {
                            popUpTo(Routes.MainRoute.Login.route) { inclusive = true }
                            launchSingleTop = true
                        }
                    } else {
                        navController.navigate(Routes.MainRoute.Home.createRoute(name)) {
                            popUpTo(Routes.MainRoute.Login.route) { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                },
                onForgotPasswordClick = { navController.toForgotPassword() },
                onSignUpClick = { navController.toSignUp() },
                //onGoogleClick =  { navController.toGoogle()}
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
                onBackClick = {navController.navigateUp()},
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
                isSignupSuccessful = {
                    viewModelSignup.resetSignup()
                    if (signupData.isAdmin) {
                        navController.navigate("${Routes.MainRoute.AdminHome.route.replace("{name}", Uri.encode(signupData.firstName))}") {
                            popUpTo(Routes.MainRoute.Login.route) { inclusive = true }
                            launchSingleTop = true
                        }
                    } else {
                        navController.navigate(Routes.MainRoute.Home.createRoute(signupData.firstName)) {
                            popUpTo(Routes.MainRoute.Login.route) { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                    Log.d("TAG", "to home")
                },
                onCreateAccountClick = {
                    viewModelSignup.validateSecondStep()
                },
                onBackClick = { viewModelSignup.onBackClick() },
                onSecondPasswordChange = { viewModelSignup.passwordRepeat(it) },
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
        composable(route = Routes.MainRoute.AdminHome.route) { navBackStackEntry ->
            val name = navBackStackEntry.arguments?.getString("name") ?: ""
            val viewModel = getViewModel<AdminHomeViewModel>()
            val data by viewModel.data.collectAsStateWithLifecycle()
            val refreshModels = navBackStackEntry.savedStateHandle.getStateFlow("refreshModels", false).collectAsStateWithLifecycle().value
            LaunchedEffect(name) {
                viewModel.setName(name)
                viewModel.loadDispensers()
            }
            LaunchedEffect(refreshModels) {
                if (refreshModels) {
                    viewModel.loadModels()
                    navBackStackEntry.savedStateHandle["refreshModels"] = false
                }
            }
            AdminHomeScreen(
                data = data,
                onTabChange = { viewModel.setTab(it) },
                onReloadDispensers = { viewModel.loadDispensers() },
                onReloadModels = { viewModel.loadModels() },
                onReloadUsers = { viewModel.loadUsers() },
                onAddModelClick = { navController.toAdminCreateDispenserModel() },
                onToProfileClick = { navController.toProfile() },
                onDispenserClick = { id, dispenserName -> navController.toDispenser(id, dispenserName) }
            )
        }
        composable(route = Routes.MainRoute.AddDispenser.route) {
            val viewModelAddDispenser = getViewModel<AddDispenserViewModel>()
            val dispenserData by viewModelAddDispenser.dispenser.collectAsStateWithLifecycle()
            LaunchedEffect(Unit, Unit) {
                viewModelAddDispenser.fetchDispenserNames()
                //viewModelAddDispenser.dispenserCount()
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
            val showHideResetPairing by dispenserViewModel.showPopUpResetPairing.collectAsStateWithLifecycle()
            LaunchedEffect(dispenserId) {
                dispenserViewModel.loadDispenser(dispenserId)
            }
            DispenserScreen(
                dispenser = dispenser,
                showHideRename = showHideRename,
                showHideDelete = showHideDelete,
                showHideResetPairing = showHideResetPairing,
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
                onResetPairingClicked = { dispenserViewModel.showHideResetPairingBack() },
                onResetPairingConfirm = { dispenserViewModel.confirmResetPairing() },
                onBackToDispenserResetPairingClicked = { dispenserViewModel.showHideResetPairingBack() },
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
                onAddScheduleClick = { navController.toAddSchedule(containerId) },
                onScheduleRowClick = { scheduleId ->
                    navController.toSchedule(
                        scheduleId = scheduleId,
                        containerName = container.pillName,
                        dispenserName = dispenserName
                    )
                },
                onRenameChange = { containerViewModel.setRenameDraft(it) },
                onRenameConfirm = { containerViewModel.confirmRename() },
                onBackToContainerRenameClicked = { containerViewModel.showHideRenameBack() },
                isRenameSuccessful = { containerViewModel.resetRename() },
            )
        }
        composable(route = Routes.MainRoute.AddSchedule.route){ navBackStackEntry ->
            val containerId = navBackStackEntry.arguments?.getString("containerId")?.toIntOrNull() ?: 0
            val addScheduleViewModel = getViewModel<AddScheduleViewModel>()
            val schedule by addScheduleViewModel.schedule.collectAsStateWithLifecycle()
            LaunchedEffect(containerId) {
                addScheduleViewModel.resetSuccess()
                addScheduleViewModel.loadSchedules(containerId)
            }
            AddScheduleScreen(
                schedule = schedule,
                onDayChange = { addScheduleViewModel.onDayChange(it) },
                onHourChange = { addScheduleViewModel.onHourChange(it) },
                onMinuteChange = { addScheduleViewModel.onMinuteChange(it) },
                onRepeatChange = { addScheduleViewModel.onRepeatChange(it) },
                onSave = { addScheduleViewModel.save(containerId) },
                onBackClick = { navController.navigateUp() }
            )
        }
        composable(route = Routes.MainRoute.Schedule.route){ navBackStackEntry ->
            val scheduleId = navBackStackEntry.arguments?.getString("scheduleId")?.toIntOrNull() ?: 0
            val containerName = navBackStackEntry.arguments?.getString("containerName")?: ""
            val dispenserName = navBackStackEntry.arguments?.getString("dispenserName") ?: ""
            val viewModel = getViewModel<ScheduleViewModel>()
            val schedule by viewModel.schedule.collectAsStateWithLifecycle()
            val showHideDelete by viewModel.showPopUpDelete.collectAsStateWithLifecycle()
            LaunchedEffect(scheduleId, containerName, dispenserName) {
                viewModel.resetSuccess()
                if (scheduleId != 0) {
                    viewModel.load(scheduleId, containerName, dispenserName)
                }
            }
            ScheduleScreen(
                schedule = schedule,
                onBackClick = { navController.navigateUp() },
                onEditToggle = { viewModel.toggleEdit() },
                onDeleteClick = { viewModel.deleteSchedule() },
                showHideDelete = showHideDelete,
                onBackToScheduleDeleteClicked = { viewModel.showHideDelete() },
                onDayChange = { viewModel.onDayChange(it) },
                onHourChange = { viewModel.onHourChange(it) },
                onMinuteChange = { viewModel.onMinuteChange(it) },
                onRepeatChange = { viewModel.onRepeatChange(it) },
                onSave = { viewModel.save() }
            )
        }
        composable(route = Routes.MainRoute.AdminCreateDispenserModel.route) {
            val viewModel = getViewModel<AdminCreateDispenserModelViewModel>()
            val state by viewModel.dispenseModel.collectAsStateWithLifecycle()
            LaunchedEffect(Unit) {
                viewModel.loadModels()
            }
            AdminCreateDispenserModelScreen(
                modelData = state,
                onCodeChange = { viewModel.onCodeChange(it) },
                onNameChange = { viewModel.onNameChange(it) },
                onSlotCountChange = { viewModel.onSlotCountChange(it) },
                onSerialPrefixChange = { viewModel.onSerialPrefixChange(it) },
                onAddModel = { viewModel.save() },
                onBackClick = {
                    viewModel.resetSuccess()
                    navController.previousBackStackEntry?.savedStateHandle?.set("refreshModels", true)
                    navController.navigateUp()
                }
            )
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
                onLogOutClicked = {
                    viewModel.performLogout {
                        navController.navigate(Routes.MainRoute.Login.route) {
                            popUpTo(Routes.MainRoute.Home.route) { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                },
                onDeleteAccountClicked = {
                    viewModel.performDelete {
                        navController.navigate(Routes.MainRoute.SignUp.route) {
                            popUpTo(Routes.MainRoute.Home.route) { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                },
                onPersonalInformation = { navController.toPersonalInformation() },
                onBackToProfileLogClicked = { viewModel.showHideLogOutBack() },  //hide popup
                onBackToProfileDeleteClicked = { viewModel.showHideDeleteBack()},  //hide popup
                //onSettings = { navController.toSettings() },
                onLogOut = { viewModel.showHideLogOutBack() }, // show popup
                onDeleteAccount = { viewModel.showHideDeleteBack() }, //show popup
                onToHomeClick = {
                    if (user.isAdmin) {
                        navController.toAdminHome(user.firstName)
                    } else {
                        navController.toHome(user.firstName)
                    }
                },
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