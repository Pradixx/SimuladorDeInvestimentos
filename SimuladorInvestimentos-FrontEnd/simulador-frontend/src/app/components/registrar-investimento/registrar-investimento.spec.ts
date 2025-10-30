import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RegistrarInvestimentoComponent } from './registrar-investimento.component';

describe('RegistrarInvestimento', () => {
  let component: RegistrarInvestimentoComponent;
  let fixture: ComponentFixture<RegistrarInvestimentoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RegistrarInvestimentoComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RegistrarInvestimentoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
