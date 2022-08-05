import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { HrTestModule } from '../../../test.module';
import { WorkEffortPurposeComponent } from 'app/entities/work-effort-purpose/work-effort-purpose.component';
import { WorkEffortPurposeService } from 'app/entities/work-effort-purpose/work-effort-purpose.service';
import { WorkEffortPurpose } from 'app/shared/model/work-effort-purpose.model';

describe('Component Tests', () => {
  describe('WorkEffortPurpose Management Component', () => {
    let comp: WorkEffortPurposeComponent;
    let fixture: ComponentFixture<WorkEffortPurposeComponent>;
    let service: WorkEffortPurposeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [WorkEffortPurposeComponent],
      })
        .overrideTemplate(WorkEffortPurposeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(WorkEffortPurposeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(WorkEffortPurposeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new WorkEffortPurpose(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.workEffortPurposes && comp.workEffortPurposes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
