import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { HrTestModule } from '../../../test.module';
import { WorkEffortAssocTypeComponent } from 'app/entities/work-effort-assoc-type/work-effort-assoc-type.component';
import { WorkEffortAssocTypeService } from 'app/entities/work-effort-assoc-type/work-effort-assoc-type.service';
import { WorkEffortAssocType } from 'app/shared/model/work-effort-assoc-type.model';

describe('Component Tests', () => {
  describe('WorkEffortAssocType Management Component', () => {
    let comp: WorkEffortAssocTypeComponent;
    let fixture: ComponentFixture<WorkEffortAssocTypeComponent>;
    let service: WorkEffortAssocTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [WorkEffortAssocTypeComponent],
      })
        .overrideTemplate(WorkEffortAssocTypeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(WorkEffortAssocTypeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(WorkEffortAssocTypeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new WorkEffortAssocType(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.workEffortAssocTypes && comp.workEffortAssocTypes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
