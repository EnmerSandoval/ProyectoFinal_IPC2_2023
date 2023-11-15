import { Component } from '@angular/core';
import { User } from 'src/app/model/User';
import { UserService } from 'src/app/services/user.service';
import { Router } from '@angular/router';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  user!: User;
  error: boolean = false;
  loginForm! : FormGroup;
  errorValues: boolean = false;

  constructor(private userService: UserService, private router: Router, private formBuilder: FormBuilder) {
    this.user = new User();
  }

  ngOnInit(){
    this.loginForm = this.formBuilder.group({
      username: [null, [Validators.required, Validators.maxLength(75)]],
      password: [null, Validators.required]
    });
  }

  onSubmit() {
    if ((this.loginForm.invalid && (this.loginForm.dirty || this.loginForm.touched)) || this.loginForm.invalid) {
      this.errorValues = true;
    } else {
      this.errorValues = false;
      this.user = { ...this.user, ...this.loginForm.value };
      this.userService.logIn(this.user).subscribe(user => {
        if (user) {
          localStorage.setItem('user', JSON.stringify(user));
          this.redirect(user);
        } 
      }, error => {console.log(this.error);
        this.error = true;
      });
    }
  }

  redirect(user : User){
    if(user.typeUser == 1){
      this.router.navigate(['/home-administrator']);
    } else if (user.typeUser == 2){
      this.router.navigate(['/home-employer']);
    } else if (user.typeUser == 3){
      this.router.navigate(['/home-applicant']);
    }
  }
}
