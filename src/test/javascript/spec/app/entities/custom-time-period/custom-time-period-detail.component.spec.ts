import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { CustomTimePeriodDetailComponent } from 'app/entities/custom-time-period/custom-time-period-detail.component';
import { CustomTimePeriod } from 'app/shared/model/custom-time-period.model';

describe('Component Tests', () => {
  describe('CustomTimePeriod Management Detail Component', () => {
    let comp: CustomTimePeriodDetailComponent;
    let fixture: ComponentFixture<CustomTimePeriodDetailComponent>;
    const route = ({ data: of({ customTimePeriod: new CustomTimePeriod(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [CustomTimePeriodDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(CustomTimePeriodDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CustomTimePeriodDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load customTimePeriod on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.customTimePeriod).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
