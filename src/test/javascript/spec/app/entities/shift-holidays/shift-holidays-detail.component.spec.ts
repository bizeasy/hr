import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { ShiftHolidaysDetailComponent } from 'app/entities/shift-holidays/shift-holidays-detail.component';
import { ShiftHolidays } from 'app/shared/model/shift-holidays.model';

describe('Component Tests', () => {
  describe('ShiftHolidays Management Detail Component', () => {
    let comp: ShiftHolidaysDetailComponent;
    let fixture: ComponentFixture<ShiftHolidaysDetailComponent>;
    const route = ({ data: of({ shiftHolidays: new ShiftHolidays(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [ShiftHolidaysDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ShiftHolidaysDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ShiftHolidaysDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load shiftHolidays on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.shiftHolidays).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
