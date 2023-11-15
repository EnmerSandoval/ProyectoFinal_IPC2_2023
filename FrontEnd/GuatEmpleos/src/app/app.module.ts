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
import { DescriptionEmployerComponent } from './components/Employer/description-employer/description-employer.component';
import { RegiterCardComponent } from './components/Employer/regster-card/regiter-card.component';
import { HomeAdministratorComponent } from './components/Administrator/home-administrator/home-administrator.component';
import { AdminAuthGuard } from './guard/AdminAuthGuard';
import { AuthService } from './services/AuthService';
import { UnauthorizedComponent } from './components/Errors/unauthorized/unauthorized.component';
import { CategorysComponent } from './components/Administrator/categorys/categorys.component';
import { UploadCvComponent } from './components/Applicant/upload-cv/upload-cv.component';
import { ApplicantAuthGuard } from './guard/ApplicantAuthGuard';
import { EmployerAuthGuard } from './guard/EmployerAuthGuard';
import { HomeEmployerComponent } from './components/Employer/home-employer/home-employer.component';
import { BidManagementComponent } from './components/Employer/bid-management/bid-management.component';
import { DatePipe } from '@angular/common';
import { SelectCategoriesComponent } from './components/Applicant/select-categories/select-categories.component';
import { HomeApplicantComponent } from './components/Applicant/home-applicant/home-applicant.component';
import { FileUploadComponent } from './components/file-upload/file-upload.component';

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
    DescriptionEmployerComponent,
    RegiterCardComponent,
    HomeAdministratorComponent,
    UnauthorizedComponent,
    CategorysComponent,
    UploadCvComponent,
    HomeEmployerComponent,
    BidManagementComponent,
    SelectCategoriesComponent,
    HomeApplicantComponent,
    FileUploadComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule
  ],
  providers: [AuthService, AdminAuthGuard, ApplicantAuthGuard, EmployerAuthGuard, DatePipe],
  bootstrap: [AppComponent]
})
export class AppModule { }
