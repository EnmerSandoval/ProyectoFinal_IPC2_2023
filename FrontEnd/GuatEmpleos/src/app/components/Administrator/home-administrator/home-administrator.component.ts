import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Category } from 'src/app/model/Category';
import { Data } from 'src/app/model/Data';
import { AdministratorService } from 'src/app/services/administrator.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-home-administrator',
  templateUrl: './home-administrator.component.html',
  styleUrls: ['./home-administrator.component.css']
})
export class HomeAdministratorComponent {
  data! : Data;
 

  constructor(private administratorService : AdministratorService, private userService : UserService) { 
    this.data = new Data();
  }

  ngOnInit(){
    this.administratorService.getViews(this.data).subscribe({
      next: (dataNew: Data) => {
        this.data = dataNew;
      }
    });
  }

  onClickLogout(){
    this.userService.logOut();
  }
}
