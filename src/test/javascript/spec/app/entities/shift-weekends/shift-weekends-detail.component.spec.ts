import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { ShiftWeekendsDetailComponent } from 'app/entities/shift-weekends/shift-weekends-detail.component';
import { ShiftWeekends } from 'app/shared/model/shift-weekends.model';

describe('Component Tests', () => {
  describe('ShiftWeekends Management Detail Component', () => {
    let comp: ShiftWeekendsDetailComponent;
    let fixture: ComponentFixture<ShiftWeekendsDetailComponent>;
    const route = ({ data: of({ shiftWeekends: new ShiftWeekends(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [ShiftWeekendsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ShiftWeekendsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ShiftWeekendsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load shiftWeekends on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.shiftWeekends).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
