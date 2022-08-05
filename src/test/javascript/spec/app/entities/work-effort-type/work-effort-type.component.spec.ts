import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { HrTestModule } from '../../../test.module';
import { WorkEffortTypeComponent } from 'app/entities/work-effort-type/work-effort-type.component';
import { WorkEffortTypeService } from 'app/entities/work-effort-type/work-effort-type.service';
import { WorkEffortType } from 'app/shared/model/work-effort-type.model';

describe('Component Tests', () => {
  describe('WorkEffortType Management Component', () => {
    let comp: WorkEffortTypeComponent;
    let fixture: ComponentFixture<WorkEffortTypeComponent>;
    let service: WorkEffortTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [WorkEffortTypeComponent],
      })
        .overrideTemplate(WorkEffortTypeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(WorkEffortTypeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(WorkEffortTypeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new WorkEffortType(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.workEffortTypes && comp.workEffortTypes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
