import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListaInvestimentosComponent } from './lista-investimentos.component';

describe('ListaInvestimentos', () => {
  let component: ListaInvestimentosComponent;
  let fixture: ComponentFixture<ListaInvestimentosComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ListaInvestimentosComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ListaInvestimentosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
