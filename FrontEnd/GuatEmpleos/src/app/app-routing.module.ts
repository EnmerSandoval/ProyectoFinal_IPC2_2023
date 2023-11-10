import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { RegisterUserComponent } from './components/register-user/register-user.component';
import { ForgotPasswordComponent } from './components/forgot-password/forgot-password.component';
import { MainHouseComponent } from './components/main-house/main-house.component';
import { RegisterTokenComponent } from './components/register-token/register-token.component';
import { ChangePasswordComponent } from './components/change-password/change-password.component';
import { HomeGuestComponent } from './components/Guest/home-guest/home-guest.component';
import { DescriptionEmployerComponent } from './components/Employer/description-employer/description-employer.component';
import { RegiterCardComponent } from './components/Employer/regster-card/regiter-card.component';
import { HomeAdministratorComponent } from './components/Administrator/home-administrator/home-administrator.component';
import { UnauthorizedComponent } from './components/Errors/unauthorized/unauthorized.component';
import { AdminAuthGuard } from './guard/AdminAuthGuard';
import { CategorysComponent } from './components/Administrator/categorys/categorys.component';

const routes: Routes = [
  { path: '', redirectTo: '/main-house', pathMatch: 'full' },
  { path: 'main-house', component: MainHouseComponent },
  { path: 'unauthorized', component: UnauthorizedComponent },
  { path: 'login', component: LoginComponent },
  { path: 'register-user', component: RegisterUserComponent },
  { path: 'forgot-password', component: ForgotPasswordComponent },
  { path: 'register-token', component: RegisterTokenComponent },
  { path: 'change-password', component: ChangePasswordComponent },
  { path: 'home-guest', component: HomeGuestComponent,  },
  { path: 'home-administrator',component: HomeAdministratorComponent,canActivate: [AdminAuthGuard]},
  { path: 'categorys', component: CategorysComponent, canActivate: [AdminAuthGuard] },


  { path: 'employer/description', component: DescriptionEmployerComponent },
  { path: 'employer/regiterCard', component: RegiterCardComponent },


];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }