import { Component } from '@angular/core';
import { Token } from '../../model/token';
import { User } from 'src/app/model/User';
import { UserService } from 'src/app/services/user.service';
import { Router } from '@angular/router';
import { ChangePasswordComponent } from '../change-password/change-password.component';

@Component({
  selector: 'app-register-token',
  templateUrl: './register-token.component.html',
  styleUrls: ['./register-token.component.css']
})
export class RegisterTokenComponent {
  token! : Token;
  user!: User;
  error: boolean = false;
  captcha : boolean = false;
  errorCaptcha : boolean = false;
  validateToken : boolean = false;

  constructor(private UserService: UserService, private router: Router) {
    this.token = new Token();
  }
   
  onSubmit() {
    if (this.captcha) {
      this.errorCaptcha = false;
      this.token.tokenDate = new Date();
      this.token.tokenDate = new Date(this.token.tokenDate.getTime() - this.token.tokenDate.getTimezoneOffset() * 60000);
      this.UserService.verifyToken(this.token).subscribe(token => {
        if (token) {
          this.error = false;
          this.validateToken = true;
          this.clean();
          localStorage.setItem('token', JSON.stringify(token));
          this.router.navigate(['/change-password']);
        } else {
          this.error = true;
        }
      }, error => {
        this.error = true;
      });
    } else {
      this.errorCaptcha = true;
    }
  }
  

  clean(){
    if (this.token) {
      this.token.token = '';
      this.captcha = false;
    }
  }
  

  
}
