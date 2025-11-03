import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CalcularRendimento } from './calcular-rendimento';

describe('CalcularRendimento', () => {
  let component: CalcularRendimento;
  let fixture: ComponentFixture<CalcularRendimento>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CalcularRendimento]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CalcularRendimento);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
