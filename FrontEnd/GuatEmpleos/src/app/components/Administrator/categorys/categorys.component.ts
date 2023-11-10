import { Component } from '@angular/core';
import { Category } from 'src/app/model/Category';
import { Commission } from 'src/app/model/Commission';
import { AdministratorService } from 'src/app/services/administrator.service';
import { UserService } from 'src/app/services/user.service';
declare var $: any;

@Component({
  selector: 'app-categorys',
  templateUrl: './categorys.component.html',
  styleUrls: ['./categorys.component.css']
})
export class CategorysComponent {
  categoryList: Category[] = [];
  category!: Category;
  newCategory! : Category;

  constructor(private userService: UserService, private administratorService: AdministratorService) {
    this.category = new Category();
    this.newCategory = new Category();
  }

  ngOnInit() {
    this.administratorService.getAllCategories().subscribe({
      next: (categories: Category[]) => {
        this.categoryList = categories;
      }
    });
  }

  showModal() {
    (window as any).$('#createCategory').modal('show');
  }

  createCategory(category: Category) {
    this.newCategory = { ...category };
    this.administratorService.insertCategories(this.newCategory).subscribe({
    });
    (window as any).$('#createCategory').modal('hide');
    this.ngOnInit();
  }

  onClickLogout() {
    this.userService.logOut();
  }

  updateCategory(category: Category) {
    this.category = { ...category };
    this.administratorService.updateCategories(this.category).subscribe({
    });
    (window as any).$('#editCategory').modal('hide');
    this.ngOnInit();
  }

  editCategory(category : Category) {
    this.category = { ...category };
    this.administratorService.selectCategory(this.category).subscribe({}
    );
  }

  showModalEdit(category: Category) {
    this.category = { ...category };
    (window as any).$('#editCategory').modal('show');
  }

}
