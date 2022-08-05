import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { LotDetailComponent } from 'app/entities/lot/lot-detail.component';
import { Lot } from 'app/shared/model/lot.model';

describe('Component Tests', () => {
  describe('Lot Management Detail Component', () => {
    let comp: LotDetailComponent;
    let fixture: ComponentFixture<LotDetailComponent>;
    const route = ({ data: of({ lot: new Lot(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [LotDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(LotDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(LotDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load lot on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.lot).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
