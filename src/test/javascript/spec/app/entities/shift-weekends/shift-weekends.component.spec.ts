import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { HrTestModule } from '../../../test.module';
import { ShiftWeekendsComponent } from 'app/entities/shift-weekends/shift-weekends.component';
import { ShiftWeekendsService } from 'app/entities/shift-weekends/shift-weekends.service';
import { ShiftWeekends } from 'app/shared/model/shift-weekends.model';

describe('Component Tests', () => {
  describe('ShiftWeekends Management Component', () => {
    let comp: ShiftWeekendsComponent;
    let fixture: ComponentFixture<ShiftWeekendsComponent>;
    let service: ShiftWeekendsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [ShiftWeekendsComponent],
      })
        .overrideTemplate(ShiftWeekendsComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ShiftWeekendsComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ShiftWeekendsService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ShiftWeekends(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.shiftWeekends && comp.shiftWeekends[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
