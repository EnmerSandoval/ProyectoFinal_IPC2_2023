import { Component } from '@angular/core';
import { User } from 'src/app/model/User';
import { UserService } from 'src/app/services/user.service';
import { Router } from '@angular/router';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';

@Component({
  selector: 'app-register-user',
  templateUrl: './register-user.component.html',
  styleUrls: ['./register-user.component.css']
})
export class RegisterUserComponent {
  user: User = new User();
  error: boolean = false;
  register: boolean = false;
  registerForm!: FormGroup;

  constructor(private userService: UserService, private router: Router, private formBuilder: FormBuilder) {

  }

  ngOnInit() {
    this.registerForm = this.formBuilder.group({
      cui: [null, [Validators.required, Validators.minLength(13), Validators.maxLength(13)]],
      name: [null, [Validators.required, Validators.maxLength(50)]],
      username: [null, [Validators.required, Validators.maxLength(75)]],
      address: [null, [Validators.required, Validators.maxLength(250)]],
      email: [null, [Validators.required, Validators.email, Validators.maxLength(250)]],
      birth: [null, Validators.required],
      typeUser: [null, Validators.required],
      phoneNumber: [null, [Validators.required, Validators.minLength(8), Validators.maxLength(8)]],
      password: [null, Validators.required]
    });
  }

  onSubmit() {
      this.user = { ...this.user, ...this.registerForm.value };
      this.userService.registerUser(this.user).subscribe(
        user => {
          localStorage.setItem('user', JSON.stringify(this.user));
          this.register = true;
          this.redirect();
        },
        error => {
          console.log(this.error);
          this.error = true;
        }
      );
   }

  redirect() {
    if (this.user.typeUser == 2) {
      this.router.navigate(['/employer/description']);
    } else if (this.user.typeUser == 3) {
      //applicant
    }
  }
}
