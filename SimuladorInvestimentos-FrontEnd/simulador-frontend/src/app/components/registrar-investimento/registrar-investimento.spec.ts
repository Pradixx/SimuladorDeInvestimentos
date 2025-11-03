import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RegistrarInvestimento } from './registrar-investimento';

describe('RegistrarInvestimento', () => {
  let component: RegistrarInvestimento;
  let fixture: ComponentFixture<RegistrarInvestimento>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RegistrarInvestimento]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RegistrarInvestimento);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
