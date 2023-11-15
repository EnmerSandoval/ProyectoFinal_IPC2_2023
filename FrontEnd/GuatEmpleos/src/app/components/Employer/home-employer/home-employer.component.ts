import { Component } from '@angular/core';
import { User } from 'src/app/model/User';


@Component({
  selector: 'app-home-employer',
  templateUrl: './home-employer.component.html',
  styleUrls: ['./home-employer.component.css']
})
export class HomeEmployerComponent {
    user!: User;
    constructor() {
      this.user = new User();
    }

    ngOnInit(){
      const storedUser = localStorage.getItem('user');
      if (storedUser) {
        this.user = JSON.parse(storedUser);
      }
    }

    onClickLogOut(){
      localStorage.clear();
    }
}
