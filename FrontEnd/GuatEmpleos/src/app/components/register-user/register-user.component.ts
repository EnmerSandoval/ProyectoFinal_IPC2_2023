import { Component } from '@angular/core';;
import { User } from 'src/app/model/User';
import { UserService } from 'src/app/services/user.service';
import { Router } from '@angular/router';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-register-user',
  templateUrl: './register-user.component.html',
  styleUrls: ['./register-user.component.css']
})
export class RegisterUserComponent {
  user! : User;
  error : boolean = false;
  register : boolean = false;

  constructor(private userService: UserService, private router: Router) {
    this.user = new User();
  } 

  onSubmit(){    
   this.userService.registerUser(this.user).subscribe(user => {
      console.log("Se entro al registro");
      if(user){
        localStorage.setItem('user', JSON.stringify(user))
        this.router.navigate(['/login']);
        this.register = true;
      }
    }, error => {
      console.log(this.error);
      this.error = true;
    });
  }
}
