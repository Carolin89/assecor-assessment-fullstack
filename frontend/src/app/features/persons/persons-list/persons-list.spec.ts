import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PersonsList } from './persons-list';

describe('PersonsList', () => {
  let component: PersonsList;
  let fixture: ComponentFixture<PersonsList>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PersonsList]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PersonsList);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
