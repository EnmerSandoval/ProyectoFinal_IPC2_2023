import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PostulationModalComponent } from './postulation-modal.component';

describe('PostulationModalComponent', () => {
  let component: PostulationModalComponent;
  let fixture: ComponentFixture<PostulationModalComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PostulationModalComponent]
    });
    fixture = TestBed.createComponent(PostulationModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
