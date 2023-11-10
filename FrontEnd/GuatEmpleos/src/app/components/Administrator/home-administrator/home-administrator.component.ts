import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Category } from 'src/app/model/Category';
import { Data } from 'src/app/model/Data';
import { AdministratorService } from 'src/app/services/administrator.service';
import { UserService } from 'src/app/services/user.service';
import { Commission } from 'src/app/model/Commission';

@Component({
  selector: 'app-home-administrator',
  templateUrl: './home-administrator.component.html',
  styleUrls: ['./home-administrator.component.css']
})
export class HomeAdministratorComponent {
  data! : Data;
  commission!: Commission;
  newCommission! : Commission;
 

  constructor(private administratorService : AdministratorService, private userService : UserService) { 
    this.data = new Data();
    this.commission = new Commission();
    this.newCommission = new Commission();
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

  showModal(){
    this.administratorService.getCommissions().subscribe({
      next: (dataNew: Commission) => {
        this.commission = dataNew;
      }
    });
    (window as any).$('#editCommission').modal('show');
  }

  updateCommission(commission : Commission){
    this.newCommission = { ...commission};
    this.administratorService.updateCommission(commission).subscribe({
    });
    this.newCommission.amount = 0;
    (window as any).$('#editCommission').modal('hide');
    this.ngOnInit();
  }
}
