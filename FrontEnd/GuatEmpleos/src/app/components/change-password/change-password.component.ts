import { Component } from '@angular/core';
import { User } from 'src/app/model/User';
import { UserService } from 'src/app/services/user.service';
import { Router } from '@angular/router';
import { Token } from 'src/app/model/token';


@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.css']
})
export class ChangePasswordComponent {
  user!: User;
  token! : Token;
  error: boolean = false;
  errorPassword: boolean = false;
  captcha: boolean = false;
  errorCaptcha: boolean = false;
  passwordTwo: string = "";
  updatePassword: boolean = false;

  constructor(private userService: UserService, private router: Router) {
    this.user = new User();
    this.token = new Token();
  }

  ngOnInit(){
    const storedToken = localStorage.getItem('token');
    if (storedToken) {
      this.token = JSON.parse(storedToken); 
    }
  }

  onSubmit() {
    this.user.cui = this.token.cuiUser;
    console.log("Token local: " + this.token.cuiUser);
    if (this.user.password == this.passwordTwo) {
      this.errorPassword = false;
      if (this.captcha) {
        this.errorCaptcha = false;
        this.userService.changePassword(this.user).subscribe(user => {
          this.error = false;
          this.clean();
          localStorage.removeItem('token'); 
          this.updatePassword = true;
        }, error => {
          console.log(this.error);
          this.error = true;
        });
       // this.router.navigate(['/login']);
      } else {
        this.errorCaptcha = true;
      }
    } else {
      this.errorPassword = true;
    }
  }

  clean(){
    this.user.password = '';
    this.passwordTwo = '';
    this.captcha = false;
  }

  redirectToMain(){
    this.router.navigate(['/main-house']);
  }

}
