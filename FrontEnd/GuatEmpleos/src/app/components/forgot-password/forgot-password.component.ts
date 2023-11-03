import { Component } from '@angular/core';
import { User } from 'src/app/model/User';
import { UserService } from 'src/app/services/user.service';
import { Router } from '@angular/router';
import { Form, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrls: ['./forgot-password.component.css']
})
export class ForgotPasswordComponent {
  user!: User;
  error: boolean = false;
  errorCaptcha: boolean = false;
  captcha: boolean = false;
  emailValid : boolean = false;

  constructor(private userService: UserService, private router: Router) {
    this.user = new User();
  }

  onSubmit() {
    if (this.captcha) {
      this.errorCaptcha = false;
      this.userService.forgotPassword(this.user).subscribe(user => {
        this.error = false;    
          console.log("Se entro al if");
          this.emailValid = true;
          this.limpiar();
      }, error => {
        console.log(this.error);
        this.error = true;
      });
    } else {
      this.errorCaptcha = true;
    }
  }

  limpiar(){
    this.user.email = '';
    this.captcha = false;
  }

  goBack(){
   // this.router.navigate(['/main-house']);
  }

}
