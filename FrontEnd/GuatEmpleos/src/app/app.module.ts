import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { LoginComponent } from './components/login/login.component';
import { HttpClientModule } from '@angular/common/http';
import { RegisterUserComponent } from './components/register-user/register-user.component';
import { ForgotPasswordComponent } from './components/forgot-password/forgot-password.component';
import { MainHouseComponent } from './components/main-house/main-house.component';
import { RegisterTokenComponent } from './components/register-token/register-token.component';
import { HomeGuestComponent } from './components/Guest/home-guest/home-guest.component';
import { ChangePasswordComponent } from './components/change-password/change-password.component';
import { ViewEmployerComponent } from './components/Guest/view-employer/view-employer.component';
import { DescriptionEmployerComponent } from './components/Employer/description-employer/description-employer.component';


@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterUserComponent,
    ForgotPasswordComponent,
    MainHouseComponent,
    RegisterTokenComponent,
    HomeGuestComponent,
    ChangePasswordComponent,
    ViewEmployerComponent,
    DescriptionEmployerComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
