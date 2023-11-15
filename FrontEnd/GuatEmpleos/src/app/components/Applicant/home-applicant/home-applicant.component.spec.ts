import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HomeApplicantComponent } from './home-applicant.component';

describe('HomeApplicantComponent', () => {
  let component: HomeApplicantComponent;
  let fixture: ComponentFixture<HomeApplicantComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [HomeApplicantComponent]
    });
    fixture = TestBed.createComponent(HomeApplicantComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
