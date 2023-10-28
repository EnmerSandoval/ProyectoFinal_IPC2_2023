import { Component } from '@angular/core';
import { User } from 'src/app/model/User';
import { UserService } from 'src/app/services/user.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  user!: User;
  error: boolean = false;

  constructor(private userService: UserService, private router: Router) {
    this.user = new User();
  }

  onSubmit() {
    this.userService.logIn(this.user).subscribe(user => {
      console.log("Se entro al login");
      if (user) {
        console.log("Se entro al if");
        localStorage.setItem('user', JSON.stringify(user));
        this.router.navigate(['/']);
      } 
    }, error => {console.log(this.error);
      this.error = true;
    });
  }
}
