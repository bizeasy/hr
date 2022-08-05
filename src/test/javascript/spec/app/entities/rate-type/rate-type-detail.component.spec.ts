import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { RateTypeDetailComponent } from 'app/entities/rate-type/rate-type-detail.component';
import { RateType } from 'app/shared/model/rate-type.model';

describe('Component Tests', () => {
  describe('RateType Management Detail Component', () => {
    let comp: RateTypeDetailComponent;
    let fixture: ComponentFixture<RateTypeDetailComponent>;
    const route = ({ data: of({ rateType: new RateType(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [RateTypeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(RateTypeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RateTypeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load rateType on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.rateType).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
