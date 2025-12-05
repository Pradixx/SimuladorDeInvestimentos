import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListarInvestimentos } from './listar-investimentos';

describe('ListaInvestimentos', () => {
  let component: ListarInvestimentos;
  let fixture: ComponentFixture<ListarInvestimentos>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ListarInvestimentos]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ListarInvestimentos);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
