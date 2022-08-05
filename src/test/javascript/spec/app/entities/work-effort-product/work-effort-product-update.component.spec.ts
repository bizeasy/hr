import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { WorkEffortProductUpdateComponent } from 'app/entities/work-effort-product/work-effort-product-update.component';
import { WorkEffortProductService } from 'app/entities/work-effort-product/work-effort-product.service';
import { WorkEffortProduct } from 'app/shared/model/work-effort-product.model';

describe('Component Tests', () => {
  describe('WorkEffortProduct Management Update Component', () => {
    let comp: WorkEffortProductUpdateComponent;
    let fixture: ComponentFixture<WorkEffortProductUpdateComponent>;
    let service: WorkEffortProductService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [WorkEffortProductUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(WorkEffortProductUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(WorkEffortProductUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(WorkEffortProductService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new WorkEffortProduct(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new WorkEffortProduct();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
