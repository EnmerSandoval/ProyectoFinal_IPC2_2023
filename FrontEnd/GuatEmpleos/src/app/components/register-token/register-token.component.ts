import { Component } from '@angular/core';
import { Token } from '../../model/token';
import { User } from 'src/app/model/User';
import { UserService } from 'src/app/services/user.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register-token',
  templateUrl: './register-token.component.html',
  styleUrls: ['./register-token.component.css']
})
export class RegisterTokenComponent {
  token!: Token;
  user!: User;
  error: boolean = false;
  captcha : boolean = false;
  errorCaptcha : boolean = false;
  validateToken : boolean = false;

  constructor(private UserService: UserService, private router: Router) {
    this.token = new Token();
  }
   
  onSubmit() {
    this.token.tokenDate = new Date();
    this.token.tokenDate.setHours(0,0,0,0);
    if(this.captcha){
      this.errorCaptcha = false;
      this.token.tokenDate = new Date();
      console.log(this.token.tokenDate);
      this.UserService.verifyToken(this.token).subscribe(token => {
        this.error = false;
        this.validateToken = true;
        this.limpiar();
      }, error => {
        this.error = true;
      });
    } else {
      this.errorCaptcha = true;
    }
  }

  limpiar(){
    this.token.token = '';
    this.captcha = false;
  }
  
}
